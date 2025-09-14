package utils


import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.util.logging.Slf4j
import org.openqa.selenium.Proxy
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeDriverService
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.chromium.ChromiumDriverLogLevel
import org.openqa.selenium.logging.LogType
import org.openqa.selenium.logging.LoggingPreferences
import org.openqa.selenium.remote.CapabilityType

import java.util.logging.Level

import static org.openqa.selenium.chrome.ChromeOptions.LOGGING_PREFS

@Slf4j
@Canonical
@EqualsAndHashCode(callSuper = true)
@ToString(includeNames = true, includeSuper = true, includePackage = false)
class CustomizedChromeDriver extends CustomizedWebDriver {

/*
    More about CHD and CHB options:

    https://developer.chrome.com/extensions/contentSettings
    https://github.com/GoogleChrome/chrome-launcher/blob/master/docs/chrome-flags-for-tools.md
    https://peter.sh/experiments/chromium-command-line-switches/
    https://issues.chromium.org/issues/42323434#comment36
    https://github.com/cyrus-and/chrome-remote-interface/issues/381
    https://issues.chromium.org/issues/40746300

 */

    enum ChromeGeoLocation {
        Allow(1), Block(2)
        Integer value

        ChromeGeoLocation(value) {
            this.value = value
        }
    }

    static ChromeOptions defaultChromeOptions() {
        ChromeOptions options = new ChromeOptions()
        options.addArguments('--enable-automation')
        options.addArguments('--disable-extensions')
        options.addArguments('--log-level=0') //minimal log level; INFO = 0, WARNING = 1, LOG_ERROR = 2, LOG_FATAL = 3
        options.addArguments('--start-maximized')
        options.addArguments('--window-position=0,0')
        options.addArguments('--window-size=1920,1080')
        return options
    }

    static ChromeDriver customizedChromeDriver(Map config) {
        ChromeOptions options = defaultChromeOptions()
        if (config?.shareLocation) {
            log.debug "Sharing location enabled"
            options.setExperimentalOption('prefs', [profile: [default_content_setting_values: [geolocation: ChromeGeoLocation.Allow.value]]])
        }
        if (config?.headless) {
            log.debug "Headless mode enabled"
            options.addArguments('--headless=new')
            options.addArguments('--no-sandbox')
            options.addArguments('--disable-dev-shm-usage')
            options.addArguments('--start-maximized')
            //options.addArguments('--remote-debugging-pipe') //Gotcha! CDP fragile!
            options.addArguments('--remote-debugging-port=0')
        }
        if (config?.extraLogs) {
            log.debug "Extra logs enabled in CHB"
            options.addArguments('--disable-breakpad')
            options.addArguments('--disable-crash-reporter')
            options.addArguments('--no-crashpad')
            options.addArguments('--enable-logging')
            options.addArguments('--v=1')
        }
        if (config?.proxy) {
            log.debug "Proxy enabled"
            options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true)
            options.setCapability(CapabilityType.PROXY, config.proxy as Proxy)
        }
        log.info "Requesting stable version of Chrome"
        options.setBrowserVersion('stable')

        ChromeDriverService.Builder serviceBuilder
        if (config?.extraLogs) {
            log.debug "Extra logs enabled in CHD"
            LoggingPreferences loggingPreferences = new LoggingPreferences();
            loggingPreferences.enable(LogType.BROWSER, Level.ALL)
            loggingPreferences.enable(LogType.CLIENT, Level.ALL)
            loggingPreferences.enable(LogType.DRIVER, Level.ALL)
            loggingPreferences.enable(LogType.SERVER, Level.ALL)
            options.setCapability(LOGGING_PREFS, loggingPreferences)
            serviceBuilder = new ChromeDriverService.Builder()
                .usingAnyFreePort()
                .withAllowedListIps('')
                .withVerbose(true)
                .withLogLevel(ChromiumDriverLogLevel.ALL)
                .withLogFile(new File('/tmp/chromedriver.log'))
                .withAppendLog(true)
                .withReadableTimestamp(true)
        } else {
            LoggingPreferences loggingPreferences = new LoggingPreferences();
            loggingPreferences.enable(LogType.BROWSER, Level.ALL)
            options.setCapability(LOGGING_PREFS, loggingPreferences)
            serviceBuilder = new ChromeDriverService.Builder()
                .usingAnyFreePort()
        }
        new ChromeDriver(serviceBuilder.build(), options)
    }
}
