import com.browserup.bup.BrowserUpProxy
import com.browserup.bup.BrowserUpProxyServer
import com.browserup.bup.filters.RequestFilter
import com.browserup.bup.filters.ResponseFilter
import com.browserup.bup.util.HttpMessageContents
import com.browserup.bup.util.HttpMessageInfo
import geb.driver.CachingDriverFactory
import geb.spock.GebReportingSpec
import groovy.util.logging.Slf4j
import io.netty.handler.codec.http.HttpRequest
import io.netty.handler.codec.http.HttpResponse
import spock.lang.Shared

import static utils.CustomizedChromeDriver.customizedChromeDriver
import static utils.CustomizedFirefoxDriver.customizedFirefoxDriver

@Slf4j
class ProxySpec extends GebReportingSpec {

    @Shared
    BrowserUpProxy proxy

    def setup() {
        proxy = new BrowserUpProxyServer()
        //proxy.trustAllServers = true
        proxy.start()

        proxy.addRequestFilter(new RequestFilter() {
            @Override
            HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
                log.info "Requested: {}", messageInfo.originalUrl
                return null
            }
        })

        proxy.addResponseFilter(new ResponseFilter() {
            @Override
            void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
                log.info "Response to: {}, with: {}", messageInfo.originalUrl, response.status()
            }
        })
    }

    def cleanup() {
        log.info "Closing proxy..."
        proxy?.stop()
        log.info "Closing webdriver..."
        driver?.quit()
        testManager.resetBrowser()
        CachingDriverFactory.clearCacheAndQuitDriver()
    }

    def "should display title section - via Firefox"() {
        given:
        driver = customizedFirefoxDriver([proxy: proxy])
        when:
        to SmokePage
        then:
        titleInContent.text() == 'httpstat.us'
    }

    def "should display title section - via Firefox headless"() {
        given:
        driver = customizedFirefoxDriver([proxy: proxy, headless: true])
        when:
        to SmokePage
        then:
        titleInContent.text() == 'httpstat.us'
    }

    def "should display title section - via Chrome"() {
        given:
        driver = customizedChromeDriver([proxy: proxy])
        when:
        to SmokePage
        then:
        titleInContent.text() == 'httpstat.us'
    }

    def "should display title section - via Chrome headless"() {
        given:
        driver = customizedChromeDriver([proxy: proxy, headless: true])
        when:
        to SmokePage
        then:
        titleInContent.text() == 'httpstat.us'
    }
}
