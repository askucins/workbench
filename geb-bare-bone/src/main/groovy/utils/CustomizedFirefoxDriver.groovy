package utils

import groovy.util.logging.Slf4j
import org.openqa.selenium.Proxy
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.CapabilityType

@Slf4j
class CustomizedFirefoxDriver extends CustomizedWebDriver {

    static FirefoxDriver customizedFirefoxDriver(Map config) {
        FirefoxOptions options = new FirefoxOptions()
        //TODO verify if needed...
        //options.setCapability('marionette', true)

        if (config?.headless) {
            options.addArguments('--window-size 1920,1080')
            options.addArguments('--headless')
        }
        if (config?.proxy) {
            log.debug "Proxy enabled"
            options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true)
            options.setCapability(CapabilityType.PROXY, config.proxy as Proxy)
        }
        new FirefoxDriver(options)
    }
}
