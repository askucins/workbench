import geb.Browser
import geb.navigator.Navigator
import geb.navigator.event.NavigatorEventListenerSupport
import org.openqa.selenium.firefox.FirefoxDriver
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.askucins.utils.CustomizedChromeDriver.customizedChromeDriver
import static org.askucins.utils.CustomizedFirefoxDriver.customizedFirefoxDriver

Logger log = LoggerFactory.getLogger("GebConfig")

environments {
    firefox {
        atCheckWaiting = 1
        driver = { new FirefoxDriver() }
    }
    firefoxHeadless {
        atCheckWaiting = 1
        driver = { customizedFirefoxDriver([headless: true]) }
    }
    chrome {
        driver = { customizedChromeDriver([headless: false]) }
    }
    chromeHeadless {
        driver = { customizedChromeDriver([headless: true]) }
    }
}

waiting {
    presets {
        slow {
            timeout = 20
            retryInterval = 1
        }
        quick {
            timeout = 1
        }
    }
}

navigatorEventListener = new NavigatorEventListenerSupport() {
    void afterClick(Browser browser, Navigator navigator) {
        // TODO This actually breaks those dynamic-navigator tests!!!
        //log.debug "${navigator*.tag()} was clicked"
        log.debug "Something was clicked!"
    }
}