// - Grapes vs Intellij
// To run in Intellij add this module/jar as a dependency: org.apache.ivy:ivy:[version]
// e.g. /home/askuci/.groovy/grapes/org.apache.ivy/ivy/jars/ivy-2.5.0.jar
// To get it installed: grape install org.apache.ivy ivy 2.5.0
//
// - Groovy 3.0.4 vs 3.0.5 command line
// 3.0.4 is the last version when it works from a command line "groovy GebSmoke.groovy" from SDKMAN's groovy...

@Grapes([
    @Grab("org.gebish:geb-core:4.0-rc-1"),
    @Grab("org.gebish:geb-spock:4.0-rc-1"),
    @Grab("org.spockframework:spock-core:2.0-M4-groovy-3.0"),
    @Grab("org.seleniumhq.selenium:selenium-chrome-driver:4.0.0-alpha-6"),
    //@Grab("org.seleniumhq.selenium:selenium-firefox-driver:4.0.0-alpha-6"),
    @GrabExclude("org.codehaus.groovy:groovy-xml"),
    @GrabExclude("org.codehaus.groovy:groovy-json"),
    @GrabExclude("org.codehaus.groovy:groovy-swing"),
    @GrabExclude("org.codehaus.groovy:groovy-datetime"),
    @GrabExclude("org.codehaus.groovy:groovy-jsr223"),
    @GrabExclude("org.codehaus.groovy:groovy-macro"),
    @GrabExclude("org.codehaus.groovy:groovy-nio"),
    @GrabExclude("org.codehaus.groovy:groovy-sql"),
])

import geb.Browser
import geb.driver.CachingDriverFactory
import geb.spock.GebReportingSpec
import spock.util.environment.RestoreSystemProperties

//import spock.lang.Ignore

@RestoreSystemProperties
class GebReportingSmokeSpec extends GebReportingSpec {

    def setupSpec() {
        System.setProperty('webdriver.chrome.driver', '/home/askuci/.gradle/webdriver/chromedriver/85.0.4183.38/chromedriver_linux64/6h50nn75cxnis2g4ls5ycnvgk/chromedriver')
        System.setProperty('webdriver.gecko.driver', '/home/askuci/.gradle/webdriver/geckodriver/0.27.0/geckodriver-v0.27.0-linux64.tar/5qh0ei3isy5x1vjh8hd5x98eq/geckodriver')
    }

    def setup() {
        testManager.resetBrowser()
        CachingDriverFactory.clearCacheAndQuitDriver()
    }

    def "should pass this test"() {
        expect:
        Browser.drive {
            go "https://gebish.org"
            assert title == "Geb - Very Groovy Browser Automation"
        }
        cleanup:
        println 'Done!'
    }

    def "should fail this test"() {
        expect:
        Browser.drive {
            go "https://gebish.org"
            assert title == "Geb - Very Groovy Browser Automationx"
        }
        cleanup:
        println 'Done!'
    }
}

