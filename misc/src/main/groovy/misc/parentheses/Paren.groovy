package misc.parentheses

class Paren {
    enum PAREN {
        LEFT('(' as char), RIGHT(')' as char)

        final char parenValue

        private PAREN(char parenValue) {
            this.parenValue = parenValue
        }

        // Reverse mapping
        static PARENS = values().collectEntries { value ->
            [(value.parenValue): value]
        }

        static PAREN findParen(Character parenValue) {
            if (parenValue == null) {
                throw new IllegalArgumentException()
            } else {
                PARENS[(parenValue)] as PAREN
            }
        }
    }
}