import geb.Browser
import geb.navigator.Navigator
import geb.navigator.event.NavigatorEventListenerSupport
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static utils.CustomizedChromeDriver.customizedChromeDriver
import static utils.CustomizedFirefoxDriver.customizedFirefoxDriver

Logger log = LoggerFactory.getLogger("GebConfig")

environments {
    firefoxBare {
        driver = { new FirefoxDriver() }
    }
    firefox {
        driver = { customizedFirefoxDriver([:]) }
    }
    firefoxHeadless {
        driver = { customizedFirefoxDriver([headless: true]) }
    }
    chromeBare {
        driver = { new ChromeDriver() }
    }
    chrome {
        driver = { customizedChromeDriver([headless: false]) }
    }
    chromeHeadless {
        driver = { customizedChromeDriver([headless: true]) }
    }
}

atCheckWaiting = true

waiting {
    presets {
        slow {
            timeout = 30
            retryInterval = 1
        }
        quick {
            timeout = 5
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

//cacheDriver = false
//cacheDriverPerThread = true