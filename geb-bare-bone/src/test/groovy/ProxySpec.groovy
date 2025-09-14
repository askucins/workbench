import geb.spock.GebReportingSpec
import groovy.util.logging.Slf4j
import org.openqa.selenium.devtools.DevTools
import spock.lang.Shared
import utils.ProxyCustom

@Slf4j
abstract class ProxySpec extends GebReportingSpec {

    static Integer bufferSizeForDevToolsNetwork = 10 * 1024 * 1024

    @Shared
    Boolean isProxyEnabled
    @Shared
    Boolean isHeadless
    @Shared
    ProxyCustom proxy
    @Shared
    DevTools devTools
    @Shared
    String timestamp

    def cleanupSpec() {
        try {
            log.debug "Cleaning webdriver and proxy after tests"
            driver?.quit()
            proxy?.stop()
        }
        catch (e) {
            log.warn "There were some issues with cleaning webdriver or proxy after tests: {}", e.getStackTrace()
        }
    }
}
