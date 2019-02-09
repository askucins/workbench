package misc.parentheses

import groovy.util.logging.Slf4j

import static Paren.*

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

        // Assumption: only parenthesis are processed further
        if (!consistsOfParensOnly(parens)) {
            throw new IllegalArgumentException()
        }

        if (parens == '') {
            return true
        }
        if (parens[0] != LEFT.value) {
            return false
        } else {
            Integer rightPosition = parens.indexOf(RIGHT.value)
            while (rightPosition > 0) {
                if (isBalanced(parens.substring(1, rightPosition)) && isBalanced(parens.substring(rightPosition + 1))) {
                    return true
                } else {
                    rightPosition = parens.indexOf(RIGHT.value, rightPosition + 1)
                }
            }
            return false
        }
    }
}
