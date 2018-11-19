package misc.parentheses

import groovy.util.logging.Slf4j

/*qa
''
'()'
'()BS'
'(BS)'
'(BS1)BS2
 */

@Slf4j
class BalancedByShape implements Balanced {
    Boolean isBalanced(String parens) {
        return false
    }
}
