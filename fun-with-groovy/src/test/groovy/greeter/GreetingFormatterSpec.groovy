package greeter

import groovy.util.logging.Slf4j
import spock.lang.Specification

@Slf4j
class GreetingFormatterSpec extends Specification {

    def 'Creating a greeting'() {
        expect: 'The greeting to be correctly capitalized'
        GreetingFormatter.greeting('gradlephant') == 'Hello, Gradlephant'
        cleanup:
        log.info "Executed test..."
    }
}