package utils

import com.browserup.bup.BrowserUpProxy
import com.browserup.bup.client.ClientUtil
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeDriverService
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.CapabilityType

class CustomizedChromeDriver {

/*
 See more about Chrome options in regard to content settings:
 https://developer.chrome.com/extensions/contentSettings
 and:
 https://github.com/GoogleChrome/chrome-launcher/blob/master/docs/chrome-flags-for-tools.md
 and:
 https://peter.sh/experiments/chromium-command-line-switches/
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
        options.addArguments('auto-ssl-client-auth')
        options.addArguments('disable-dev-shm-usage')
        options.addArguments('disable-extensions')
        options.addArguments('disable-gpu')
        options.addArguments('log-level=0') //minimal log level; INFO = 0, WARNING = 1, LOG_ERROR = 2, LOG_FATAL = 3
        //options.addArguments('start-maximized')
        options.addArguments('window-position=0,0')
        options.addArguments('window-size=1920,1080')
        //workarounds for that high DPI odd behaviour
        //options.addArguments('device-scale-factor=2')
        //options.addArguments('ash-host-window-bound=1920x1080*4')
        //options.addArguments('force-device-scale-factor=2') //!
        //TODO double check if really needed...
        //options.addArguments('no-sandbox')
        //options.addArguments('dns-prefetch-disable')
        //options.addArguments('disable-browser-side-navigation')

        //options.addArguments('enable-automation')
        options.setExperimentalOption('excludeSwitches', ['enable-automation'])
        // TODO it seems deprecated...
        //options.setExperimentalOption('useAutomationExtension', true)

        options.setExperimentalOption('prefs', [profile: [default_content_setting_values: [geolocation: ChromeGeoLocation.Block.value]]])

        return options
    }

    static ChromeDriver customizedChromeDriver(Map config) {
        ChromeOptions options = defaultChromeOptions()
        if (config.shareLocation) {
            options.setExperimentalOption("prefs", [profile: [default_content_setting_values: [geolocation: ChromeGeoLocation.Allow.value]]])
        }
        if (config?.headless) {
            options.addArguments('headless')
        }
        if (config?.proxy) {
            // Host defined explicitly to enforce using localhost/127.0.0.1
            def seleniumProxy = ClientUtil.createSeleniumProxy(config.proxy as BrowserUpProxy, InetAddress.getByName('localhost'))
            options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true)
            options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true)
            options.setCapability(CapabilityType.PROXY, seleniumProxy)
        }
        ChromeDriverService.Builder serviceBuilder = new ChromeDriverService.Builder().usingAnyFreePort()
        new ChromeDriver(serviceBuilder.build(), options)
    }
}
