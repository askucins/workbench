// To run in Intellij add this module/jar as a dependency: org.apache.ivy:ivy:[version]
// To get it installed: grape install org.apache.ivy ivy 2.5.0
@Grapes([
    @Grab("org.gebish:geb-core:3.4"),
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
])

import geb.Browser

System.setProperty('webdriver.chrome.driver', '/home/askuci/.gradle/webdriver/chromedriver/81.0.4044.138/chromedriver_linux64/cw2ad4f0dy1a6ngf4tmo5n56p/chromedriver')

Browser.drive {
    go "https://gebish.org"
    assert title == "Geb - Very Groovy Browser Automation"
}.quit()

System.setProperty('webdriver.chrome.driver', null)

println 'Done!'
