package misc.parentheses

class Paren {
    enum PAREN {
        LEFT('('), RIGHT(')')

        final String parenValue

        private PAREN(String parenValue) {
            this.parenValue = parenValue
        }

        // Reverse mapping
        static PARENS = values().collectEntries { value ->
            [(value.parenValue): value]
        }

        static PAREN findParen(String parenValue) {
            if (parenValue == null) {
                throw new IllegalArgumentException()
            } else {
                PARENS[(parenValue)] as PAREN
            }
        }
    }
}