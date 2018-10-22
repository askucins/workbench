package greeter

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

@Slf4j
@CompileStatic
class GreetingFormatter {
    static String greeting(final String name) {
        log.info "Processing <<greeting>> for [{}]", name
        "Hello, ${name.capitalize()}"
    }
}