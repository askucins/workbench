package misc

import groovy.util.logging.Slf4j
import spock.lang.Specification

@Slf4j
class ClosuresSpec extends Specification {

    def "should demonstrate how closure work"() {
        when:
        Closure cl = {
            String external = 'foo'
            log.info "External|it: {}|{}", external, it
            Closure whatever = {
                String internal = 'bar'
                String result = "${external}|${it}|${internal}"
                log.info "External|it|internal: {}", result
                result
            }
        }
        then:
        cl('a') instanceof Closure
        and:
        cl('a')('b') == 'foo|b|bar'
    }
}
