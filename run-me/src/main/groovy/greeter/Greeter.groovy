package greeter

import groovy.util.logging.Slf4j
import org.slf4j.MDC

@Slf4j
class Greeter {
    static void main(String... args) {
        MDC.put('correlation', UUID.randomUUID().toString())
        log.info "Executing main()"
        log.debug "Executing main() with arguments [{}]", args
        println GreetingFormatter.greeting(args[0])
        MDC.remove('correlation')
        log.debug "The end."
    }
}