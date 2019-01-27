package misc.parentheses

import groovy.util.logging.Slf4j

import static misc.parentheses.Paren.PAREN

@Slf4j
class BalancedNaive implements Balanced {

    Boolean isBalanced(String parens) {
        Integer parensLeft = 0
        Integer parensRight = 0

        //TODO - it works, bus is this a nice one?
        if (parens =~ /[^${PAREN.LEFT}${PAREN.RIGHT})]/) {
            throw new IllegalArgumentException()
        }

        for (String paren in parens) {
            switch (PAREN.findParen(paren)) {
                case (PAREN.LEFT):
                    parensLeft++
                    break
                case (PAREN.RIGHT):
                    parensRight++
                    if (parensLeft < parensRight) {
                        return false
                    }
                    break
                default:
                    throw new IllegalArgumentException()
                    break
            }
        }
        return (parensLeft == parensRight)
    }
}
