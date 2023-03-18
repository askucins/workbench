// - Grapes vs Intellij
// To run in Intellij add this module/jar as a dependency: org.apache.ivy:ivy:[version]
// e.g. /home/askuci/.groovy/grapes/org.apache.ivy/ivy/jars/ivy-2.5.0.jar
// To get it installed: grape install org.apache.ivy ivy 2.5.0

// As of 2023-02-19 it works with Groovy 4.0.10
// Known issues: that com.beust:jcommander:1.82 has to be downloaded manually!

@Grapes([
    @Grab("org.gebish:geb-core:7.0"),
    @Grab("org.gebish:geb-spock:7.0"),
    @Grab("org.spockframework:spock-core:2.4-M1-groovy-4.0"),
    @Grab("org.seleniumhq.selenium:selenium-support:4.8.1"),
    @Grab("org.seleniumhq.selenium:selenium-chrome-driver:4.8.1"),
    @Grab("org.seleniumhq.selenium:selenium-devtools-v110:4.8.1"),
    @Grab("io.netty:netty-buffer:4.1.89.Final"),
    @Grab("com.beust:jcommander:1.82"),
    @GrabExclude(group = "io.netty", module = "netty-codec-socks"),
    @GrabExclude(group = "io.netty", module = "netty-handler-proxy"),
    @GrabExclude("org.apache.groovy:groovy-xml"),
    @GrabExclude("org.apache.groovy:groovy-macro")
])

import geb.Browser
import geb.spock.GebReportingSpec
import spock.util.environment.RestoreSystemProperties

@RestoreSystemProperties
class GebReportingSmokeSpec extends GebReportingSpec {

    def setupSpec() {
        System.setProperty('webdriver.chrome.driver', '/home/askuci/.gradle/webdriver/chromedriver/110.0.5481.77/chromedriver_linux64/97eqo56c0rf1sdkxgnwf50it2/chromedriver')
        System.setProperty('webdriver.gecko.driver', '/home/askuci/.gradle/webdriver/geckodriver/0.32.0/geckodriver-v0.32.0-linux64.tar/al74c7b0kgczcdd5kqtkw5db6/geckodriver')
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

