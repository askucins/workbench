package misc

import groovy.util.logging.Slf4j
import spock.lang.Specification

@Slf4j
class GroovyRelease3Spec extends Specification {

    def "should cover other loops"() {
        given:
        def expectedResult = [0, 9, 16, 21, 24, 25]
        def entries = []
        when:
        for (int i = 0, j = 10; i <= j; i++, j--) {
            int chunk = i * j
            entries << chunk
            log.info "i={}, j={}, i*j={}", i, j, chunk
        }
        then:
        entries.size() == expectedResult.size()
        and:
        entries == expectedResult
    }
}