@Grapes([
        @Grab("org.gebish:geb-core:3.2"),
        //@Grab("org.seleniumhq.selenium:selenium-firefox-driver:4.0.0-alpha-3"),
        @Grab("org.seleniumhq.selenium:selenium-chrome-driver:4.0.0-alpha-3"),
        @Grab("org.seleniumhq.selenium:selenium-support:4.0.0-alpha-3"),
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

Browser.drive {
    go "http://gebish.org"
    assert title == "Geb - Very Groovy Browser AutomationX"
}

