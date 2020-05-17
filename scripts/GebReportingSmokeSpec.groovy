// To run in Intellij add this module/jar as a dependency: org.apache.ivy:ivy:[version]
// To get it installed: grape install org.apache.ivy ivy 2.5.0

@Grapes([
    //@Grab("org.seleniumhq.selenium:selenium-firefox-driver:4.0.0-alpha-5"),
    @Grab("org.seleniumhq.selenium:selenium-chrome-driver:4.0.0-alpha-5"),
    @Grab("org.seleniumhq.selenium:selenium-support:4.0.0-alpha-5"),
    @GrabExclude("org.codehaus.groovy:groovy-xml"),
    @GrabExclude("org.codehaus.groovy:groovy-json"),
    @GrabExclude("org.codehaus.groovy:groovy-swing"),
    @GrabExclude("org.codehaus.groovy:groovy-datetime"),
    @GrabExclude("org.codehaus.groovy:groovy-jsr223"),
    @GrabExclude("org.codehaus.groovy:groovy-macro"),
    @GrabExclude("org.codehaus.groovy:groovy-nio"),
    @GrabExclude("org.codehaus.groovy:groovy-sql"),
    @Grab("org.spockframework:spock-core:2.0-M2-groovy-3.0"),
    @Grab("org.spockframework:spock-junit4:2.0-M2-groovy-3.0"),
    @Grab("org.gebish:geb-core:3.4"),
    @Grab("org.gebish:geb-spock:3.4")
])

import geb.Browser
import geb.driver.CachingDriverFactory
import geb.spock.GebReportingSpec
import spock.util.environment.RestoreSystemProperties

@RestoreSystemProperties
class GebReportingSmokeSpec extends GebReportingSpec {

    def setupSpec() {
        System.setProperty('webdriver.chrome.driver', '/home/askuci/.gradle/webdriver/chromedriver/81.0.4044.138/chromedriver_linux64/cw2ad4f0dy1a6ngf4tmo5n56p/chromedriver')
    }

    def setup() {
        resetBrowser()
        CachingDriverFactory.clearCacheAndQuitDriver()
    }

    def "smoke test"() {
        expect:
        Browser.drive {
            go "https://gebish.org"
            assert title == "Geb - Very Groovy Browser Automation"
        }
        cleanup:
        println 'Done!'
    }

    def "smoke test 2"() {
        expect:
        Browser.drive {
            go "https://gebish.org"
            assert title == "Geb - Very Groovy Browser Automationx"
        }
        cleanup:
        println 'Done!'
    }
}

