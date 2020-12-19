package chap02a

import ratpack.groovy.Groovy
import ratpack.server.RatpackServer


class Main {
    static void main(String... args) {
        RatpackServer.start { spec ->
            spec.handlers(Groovy.chain {
                get {
                    render "Hello, world!"
                }
            })
        }
    }
}

