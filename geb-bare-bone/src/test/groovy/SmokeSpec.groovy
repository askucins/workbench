import geb.spock.GebReportingSpec
import groovy.util.logging.Slf4j
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.devtools.DevTools
import org.openqa.selenium.devtools.v142.network.Network
import org.openqa.selenium.devtools.v142.storage.Storage
import org.openqa.selenium.logging.LogType
import spock.lang.Shared

import static utils.CustomizedChromeDriver.customizedChromeDriver

@Slf4j
class SmokeSpec extends GebReportingSpec {

    static Integer bufferSizeForDevToolsNetwork = 10 * 1024 * 1024

    @Shared
    DevTools devTools

    def setupSpec() {
        log.info "Starting custom webdriver"
        browser.driver = customizedChromeDriver([headless: false])
        browser.baseUrl = SmokePage.url
        log.info "[baseUrl] {}", baseUrl
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

    def cleanupSpec() {
        try {
            log.debug "Cleaning webdriver and proxy after tests"
            driver?.quit()
        }
        catch (e) {
            log.warn "There were some issues with cleaning webdriver or proxy after tests: {}", e.getStackTrace()
        }
    }

    def "should display title section"() {
        when:
        to SmokePage
        then:
        titleInContent.text() == 'ifconfig.co — What is my IP address?'
    }
}