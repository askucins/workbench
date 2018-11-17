package misc.parentheses

import groovy.util.logging.Slf4j
import spock.lang.Specification
import static misc.parentheses.Paren.PAREN

@Slf4j
class ParenSpec extends Specification {

    def "should find the left parenthesis"() {
        expect:
        PAREN.findParen('(' as char) == PAREN.LEFT
    }

    def "should find the right parenthesis"() {
        expect:
        PAREN.findParen(')' as char) == PAREN.RIGHT
    }

    def "should not match non-parenthesis"() {
        expect:
        PAREN.findParen('x' as char) == null
    }

    def "should throw an exception on null"() {
        when:
        PAREN.findParen(null) == null
        then:
        thrown IllegalArgumentException
    }
}