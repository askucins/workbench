package utils

import com.browserup.bup.BrowserUpProxy
import com.browserup.bup.client.ClientUtil
import groovy.util.logging.Slf4j
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.CapabilityType

@Slf4j
class CustomizedFirefoxDriver {

    static FirefoxDriver customizedFirefoxDriver(Map config) {
        FirefoxOptions options = new FirefoxOptions()
        options.setCapability('marionette', true)

        if (config?.headless) {
            options.addArguments('--window-size 1920,1080')
            options.addArguments('--headless')
        }
        if (config?.proxy) {
            // Host defined explicitly to enforce using localhost/127.0.0.1
            def seleniumProxy = ClientUtil.createSeleniumProxy(config.proxy as BrowserUpProxy, InetAddress.getByName('localhost'))
            options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true)
            options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true)
            options.setCapability(CapabilityType.PROXY, seleniumProxy)
        }
        new FirefoxDriver(options)
    }
}
