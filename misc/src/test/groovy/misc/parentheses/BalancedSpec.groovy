package misc.parentheses

import groovy.util.logging.Slf4j
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Unroll

import static misc.parentheses.Balanced.isBalanced


@Narrative('''
Based on http://raganwald.com/2018/10/17/recursive-pattern-matching.html
To determine, whether a string of parentheses is balanced 
''')
@Slf4j
class BalancedSpec extends Specification {

    @Unroll
    def "should check if |#parens| is balanced (#balanced; #comment)"() {
        expect:
        isBalanced(parens) == balanced
        where:
        parens       | balanced | comment
        ''           | true     | 'empty string, balanced!'
        '()'         | true     | 'obvious one'
        '(())'       | true     | 'parentheses can nest'
        '()()'       | true     | 'multiple pairs are acceptable'
        '(()()())()' | true     | 'multiple pairs can nest'
        '((()'       | false    | 'missing closing parentheses'
        '()))'       | false    | 'missing opening parentheses'
        ')('         | false    | 'close before open'
    }
}