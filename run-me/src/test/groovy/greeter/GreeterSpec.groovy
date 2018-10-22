package greeter

import groovy.util.logging.Slf4j
import spock.lang.Specification

@Slf4j
class GreeterSpec extends Specification {

    def 'Calling the entry point'() {

        setup: 'Re-route standard out'
        def buf = new ByteArrayOutputStream(1024)
        System.out = new PrintStream(buf)

        when: 'The entrypoint is executed'
        Greeter.main('gradlephant')

        then: 'The correct greeting is output'
        buf.toString() == "Hello, Gradlephant\n".denormalize()

        cleanup:
        log.info "Test executed..."
    }
}