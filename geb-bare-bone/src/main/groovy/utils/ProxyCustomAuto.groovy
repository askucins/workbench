package utils

import com.browserup.bup.BrowserUpProxy
import com.browserup.bup.BrowserUpProxyServer
import com.browserup.bup.client.ClientUtil
import com.browserup.bup.filters.RequestFilter
import com.browserup.bup.filters.ResponseFilter
import com.browserup.bup.util.HttpMessageContents
import com.browserup.bup.util.HttpMessageInfo
import groovy.transform.Canonical
import groovy.transform.ToString
import groovy.util.logging.Slf4j
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.http.HttpHeaderNames
import io.netty.handler.codec.http.HttpRequest
import io.netty.handler.codec.http.HttpResponse
import org.brotli.dec.BrotliInputStream
import org.littleshoot.proxy.HttpFilters
import org.littleshoot.proxy.HttpFiltersSource

@Slf4j
@Canonical
@ToString(includeNames = true, includePackage = false, includeSuper = true)
class ProxyCustomAuto extends ProxyCustom {

    private BrowserUpProxy browserUpProxy

    private static BrowserUpProxy createProxy() {
        log.debug("Creating proxy...")
        //Gotcha! This counter might be helpful, but if responses come with different order then it doesn't know...
        Integer counter = 0
        String marker = 'QA-'

        def shouldFullLogInProxy = { String url ->
            List<String> excludedUrls = ['.css', '.jpg', '.png', '.svg', '.js', 'google', 'facebook', 'fb', 'fonts.gstatic.com', 'google']
            List<String> includedUrls = ['ifconfig.co']

            (!excludedUrls.any { url.contains(it) }
                || includedUrls.any { url.contains(it) })
        }

        BrowserUpProxy proxy = new BrowserUpProxyServer()
        proxy.start(0)

        proxy.addFirstHttpFilterFactory(new HttpFiltersSource() {
            @Override
            HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) { return null }

            @Override
            int getMaximumRequestBufferSizeInBytes() { return 100 * 1024 * 1024 }

            @Override
            int getMaximumResponseBufferSizeInBytes() { return 100 * 1024 * 1024 }
        })

        //TODO change that configuration above to something like this:
        //proxy.addFirstHttpFilterFactory(new RequestFilterAdapter.FilterSource(filter, 10 * 1024 * 1024))

        //TODO change this to more like Java8 syntax as in https://github.com/browserup/browserup-proxy#littleproxy-interceptors
        proxy.addRequestFilter(new RequestFilter() {
            @Override
            HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
                String headers = request.headers().entries()*.toString().join('|')
                if (shouldFullLogInProxy(messageInfo.originalUrl)) {
                    log.debug "${marker}${counter}-Req0 {} {}", request.method().name(), messageInfo.originalUrl
                    log.debug "${marker}${counter}-Req1 headers: {}", "[${headers}]"
                    if (request.headers().contains('content-type', 'application/json', true) ||
                        request.headers().contains('content-type', 'application/json;charset=UTF-8', true) ||
                        request.headers().contains('content-type', 'application/x-www-form-urlencoded', true)
                    ) {
                        log.debug "${marker}${counter}-Req2 content: {}", contents.textContents
                    }
                } else {
                    log.debug "${marker}${counter}-Req3 {} {}", request.method().name(), messageInfo.originalUrl
                    log.debug "${marker}${counter}-Req4 headers: {}", "[${headers}]"

                }
                counter++
                return null
            }
        })

        proxy.addResponseFilter(new ResponseFilter() {
            @Override
            void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
                String headers = response.headers().entries()*.toString().join('|')
                if (shouldFullLogInProxy(messageInfo.originalUrl)) {
                    log.debug "${marker}${counter}-Res0 url {}", messageInfo.originalUrl
                    log.debug "${marker}${counter}-Res1 headers: {}", "[${headers}]"
                    //TODO make this filtering smarter!
                    if (response.headers().contains('content-type', 'application/json', true) ||
                        response.headers().contains('content-type', 'application/json; charset=UTF-8', true) ||
                        response.headers().contains('content-type', 'application/json;charset=UTF-8', true) ||
                        response.headers().contains('content-type', 'text/plain;charset=ISO-8859-1', true)
                    ) {
                        if (response.headers().get(HttpHeaderNames.CONTENT_ENCODING)?.toLowerCase()?.contains('br')) {
                            log.debug "${marker}${counter}-Res2 content: {}", new BrotliInputStream(new ByteArrayInputStream(contents.binaryContents)).text
                        } else {
                            log.debug "${marker}${counter}-Res2 content: {}", contents.textContents
                        }
                    }
                } else {
                    log.debug "${marker}${counter}-Res3 url {}", messageInfo.originalUrl
                    log.debug "${marker}${counter}-Res4 headers: {}", "[${headers}]"
                }
            }
        })

        /*
            Actually it seems, that within HTTP/1.1
            we may really force that regardless of presence of original Connection.

            Source: that O'Reilly book on HTTP - a difference between HTTP/1.0 and HTTP/1.1
            namely: keep-alive connections vs persistent connections
        */
//        proxy.addResponseFilter(new ResponseFilter() {
//            @Override
//            void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
//                String connectionHeader = response.headers().getAll('Connection')
//                if (connectionHeader) {
//                    log.trace "Found Connection header!"
//                    response.headers().set('Connection', 'close')
//                }
//            }
//        })

        return proxy
    }

    ProxyCustomAuto() {
        browserUpProxy = createProxy()
        // Host defined explicitly to enforce using localhost/127.0.0.1
        proxy = ClientUtil.createSeleniumProxy(browserUpProxy, InetAddress.getByName('localhost'))
        this
    }

    void stop() {
        log.info "Auto proxy will be stopped!"
        browserUpProxy?.stop()
    }
}
