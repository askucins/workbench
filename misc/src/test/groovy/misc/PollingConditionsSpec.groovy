package misc

import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

@Slf4j
class PollingConditionsSpec extends Specification {

    def "should work"() {
        given:
        def person = new Person(name: 'Fred', age: 22)
        def conditions = new PollingConditions(timeout: 10)

        when:
        Thread.start {
            log.info "About to fall asleep..."
            sleep(1000)
            person.age = 42
            log.info "About to fall asleep again..."
            sleep(5000)
            person.name = "Barney"
            log.info "Done with sleep"
        }

        then:
        conditions.within(2) {
            assert person.age == 42
        }

        conditions.eventually {
            assert person.name == "Barney"
        }
    }
}