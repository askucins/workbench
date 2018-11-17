package misc.parentheses

import static misc.parentheses.Paren.PAREN

class BalancedNaive implements Balanced {

    Boolean isBalanced(String parens) {
        Integer parensLeft = 0
        Integer parensRight = 0

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
