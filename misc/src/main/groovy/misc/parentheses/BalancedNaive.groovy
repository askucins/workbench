package misc.parentheses

import groovy.util.logging.Slf4j

import static Paren.*

@Slf4j
class BalancedNaive implements Balanced {

    Boolean isBalanced(String parens) {
        Integer parensLeft = 0
        Integer parensRight = 0

        // Assumption: only parenthesis are processed further
        if (!consistsOfParensOnly(parens)) {
            throw new IllegalArgumentException()
        }

        for (String paren in parens) {
            switch (findParen(paren)) {
                case (LEFT):
                    parensLeft++
                    break
                case (RIGHT):
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
