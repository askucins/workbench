// - Grapes vs Intellij
// To run in Intellij add this module/jar as a dependency: org.apache.ivy:ivy:[version]
// e.g. /home/askuci/.groovy/grapes/org.apache.ivy/ivy/jars/ivy-2.5.0.jar
// To get it installed: grape install org.apache.ivy ivy 2.5.0
//
// - Groovy 3.0.4 vs 3.0.5 command line
// 3.0.4 is the last version when it works from a command line "groovy GebSmoke.groovy" from SDKMAN's groovy...

@Grapes([
    @Grab("org.gebish:geb-core:3.4"),
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

System.setProperty('webdriver.chrome.driver', '/home/askuci/.gradle/webdriver/chromedriver/85.0.4183.38/chromedriver_linux64/6h50nn75cxnis2g4ls5ycnvgk/chromedriver')
System.setProperty('webdriver.gecko.driver', '/home/askuci/.gradle/webdriver/geckodriver/0.26.0/geckodriver-v0.26.0-linux64.tar/d873k1x1c8078m6sc0ysb8ijy/geckodriver')

Browser.drive {
    go "https://gebish.org"
    assert title == "Geb - Very Groovy Browser Automation"
}.quit()

System.clearProperty('webdriver.chrome.driver')
System.clearProperty('webdriver.gecko.driver')

println 'Done!'
