package parameterize

import groovy.util.logging.Slf4j
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Unroll

@Slf4j
@Stepwise
abstract class ParameterizeSpec extends Specification {

    @Shared
    Config config

    @Unroll
    def "should retrieve config id from environment (#instance.id, #attempt)"() {
        expect:
        instance.id
        cleanup:
        log.info "ID: {}", instance.id
        where:
        attempt << (0..4)
        instance = config
    }

    @Unroll
    def "should retrieve config label from environment (#instance.id, #attempt)"() {
        expect:
        instance.label.startsWith('This is')
        cleanup:
        log.info "Label: {}", instance.label
        where:
        attempt << (0..1)
        instance = config
    }

}
