import groovy.util.logging.Slf4j
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.devtools.v138.network.Network
import org.openqa.selenium.devtools.v138.storage.Storage
import org.openqa.selenium.logging.LogType
import utils.ProxyCustomAuto

import static utils.CustomizedChromeDriver.customizedChromeDriver

@Slf4j
class ProxyWhenChromeSpec extends ProxySpec {

    def setupSpec() {
        isProxyEnabled = true
        isHeadless = false

        log.info "Starting proxy if needed"
        if (isProxyEnabled) {
            proxy = new ProxyCustomAuto()
        } else {
            proxy = null
        }
        log.info "Starting custom webdriver"
        browser.driver = customizedChromeDriver([headless: isHeadless, proxy: proxy?.proxy(), shareLocation: true])
        browser.baseUrl = SmokePage.url
        log.info "[baseUrl] {}", baseUrl
        timestamp = System.currentTimeMillis().toString()
        log.info "Enabling DevTools"
        devTools = (driver as ChromeDriver).getDevTools()
        devTools.createSession()
        devTools.send(Network.enable(Optional.of(bufferSizeForDevToolsNetwork), Optional.of(bufferSizeForDevToolsNetwork), Optional.of(bufferSizeForDevToolsNetwork), Optional.of(true)))
        devTools.addListener(
            Network.requestWillBeSent(),
            { requestWillBeSent ->
                if (requestWillBeSent.request.url =~ /cdn-cgi\/challenge-platform/) {
                    log.warn "Found request to /cdn-cgi/challenge-platform!"
                    log.warn "[URL] {}", requestWillBeSent.request.url
                }
                if (requestWillBeSent.request.url =~ /cdn-cgi\/challenge-platform/) {
                    log.warn "Found response from /cdn-cgi/challenge-platform!"
                    log.warn "[URL] {}", requestWillBeSent.request.url
                }
            }
        )
    }

    def setup() {
        log.info ">>>> ${this.specificationContext.currentFeature.name}"
        log.info "Collecting all cookies via DevTools"
        log.info "Cookies:\n{}", devTools.send(Storage.getCookies(Optional.empty())).collect { "[Cookie] ${it.name}:${it.value}:${it.domain}" }.join('\n')
        log.info "Collecting console logs"
        log.info "Console logs:\n{}", driver.manage().logs().get(LogType.BROWSER).getAll()*.toString().join('\n')
    }

    def cleanup() {
        log.info "Collecting all cookies via DevTools"
        log.info "Cookies:\n{}", devTools.send(Storage.getCookies(Optional.empty())).collect { "[Cookie] ${it.name}:${it.value}:${it.domain}" }.join('\n')
        log.info "Collecting console logs"
        driver.manage().logs().availableLogTypes.each { String logType ->
            List<String> logs = driver.manage().logs().get(logType).getAll()*.toString()
            log.info "[{}]\n{}", logType, logs ? logs.join('\n') : ''
        }
    }

    def "should display title section - via Chrome"() {
        when:
        to SmokePage
        then:
        titleInContent.text() == 'ifconfig.co — What is my IP address?'
    }
}
