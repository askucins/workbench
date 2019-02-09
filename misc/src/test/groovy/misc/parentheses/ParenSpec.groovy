package misc.parentheses

import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Unroll

import static misc.parentheses.Paren.*

@Slf4j
class ParenSpec extends Specification {

    def "should find the left parenthesis"() {
        expect:
        findParen('(') == LEFT
    }

    def "should find the right parenthesis"() {
        expect:
        findParen(')') == RIGHT
    }

    @Unroll
    def "should not match non-parenthesis: |#nonParen|"() {
        expect:
        findParen(nonParen) == null
        where:
        nonParen | _
        'x'      | _
        '()'     | _
        ''       | _
        ' '      | _
    }

    def "should throw an exception on null"() {
        when:
        findParen(null) == null
        then:
        thrown IllegalArgumentException
    }

    @Unroll
    def "should contain only parenthesis |#parensLike|"() {
        expect:
        consistsOfParensOnly(parensLike) == outcome
        where:
        parensLike | outcome
        ''         | true
        ' '        | false
        '('        | true
        ')'        | true
        '()'       | true
        ')('       | true
        '(('       | true
        '))'       | true
        'x'        | false
        '(x)'      | false
    }
}