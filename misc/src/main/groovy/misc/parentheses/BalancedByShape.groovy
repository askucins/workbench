package misc.parentheses

import groovy.util.logging.Slf4j

/*
''
'()'
'()BS'
'(BS)'
'(BS1)BS2
 */

@Slf4j
class BalancedByShape implements Balanced {
    Boolean isBalanced(String parens) {
        if (parens == '') {
            return true
        }
        if (parens == '()') {
            return true
        }
        def subparens = parens =~ /^\((.+)\)$/
        if (subparens) {
            return isBalanced(subparens[0][1] as String)
        } else {
            return false
        }
    }

}
