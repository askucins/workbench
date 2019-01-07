package misc.parentheses

import groovy.util.logging.Slf4j
import spock.lang.Narrative
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

@Narrative('''
Based on http://raganwald.com/2018/10/17/recursive-pattern-matching.html
To determine, whether a string of parentheses is balanced 
''')
@Slf4j
abstract class BalancedSpec extends Specification {
    @Shared
    Balanced balancer

    @Unroll
    def "should check if |#parens| is balanced (#balanced; #comment)"() {
        expect:
        balancer.isBalanced(parens as String) == balanced
        where:
        parens               | balanced | comment
        ''                   | true     | 'empty string, balanced!'
        '()'                 | true     | 'obvious one'
        '(())'               | true     | 'parentheses can nest'
        '()()'               | true     | 'multiple pairs are acceptable'
        '(()()())()'         | true     | 'multiple pairs can nest'
        '((()'               | false    | 'missing closing parentheses'
        '()))'               | false    | 'missing opening parentheses'
        ')('                 | false    | 'close before open'
        //
        '((()()))'           | true     | 'nested'
        '((()((()))'         | false    | 'left more than right'
        '('                  | false    | 'only left'
        '(('                 | false    | 'only left (more)'
        '((('                | false    | 'only left (more)'
        ')'                  | false    | 'only right'
        '))'                 | false    | 'only right (more)'
        ')))'                | false    | 'only right (more)'
        '()()()()'           | true     | 'multiple pairs are ok'
        '(())(())(((()())))' | true     | 'multiple nested are ok'
        ')()('               | false    | 'close before open'
    }

    def "should throw an exception on invalid argument"() {
        when:
        balancer.isBalanced('x')
        then:
        thrown IllegalArgumentException
    }
}