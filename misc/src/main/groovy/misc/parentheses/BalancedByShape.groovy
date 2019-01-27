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
        //TODO - it works, bus is this a nice one?
        if (parens =~ /[^()]/) {
            throw new IllegalArgumentException()
        }
        if (parens == '') {
            return true
        }
        if (parens[0] != '(') {
            return false
        } else {
            Integer rightPosition = parens.indexOf(')')
            while (rightPosition > 0) {
                if (isBalanced(parens.substring(1, rightPosition)) && isBalanced(parens.substring(rightPosition + 1))) {
                    return true
                } else {
                    rightPosition = parens.indexOf(')', rightPosition + 1)
                }
            }
            return false
        }
    }
}
