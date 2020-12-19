@GrabResolver(name = "lightweightest", m2Compatible = 'true', root = 'https://raw.github.com/dmitart/lightweightest/master/repository')
@Grab("org.lightweightest:lightweightest:0.5")

import org.lightweightest.Lightweightest

Lightweightest.start(port: 9999, stopAfter: 1) {
    get("/card/list") { request, response ->
        "qwerty"
    }
}
println "http://localhost:9999/card/list".toURL().text
