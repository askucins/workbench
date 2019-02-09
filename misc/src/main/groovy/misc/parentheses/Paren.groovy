package misc.parentheses


enum Paren {
    LEFT('('), RIGHT(')')

    final String value

    private Paren(String value) {
        this.value = value
    }

    // Reverse mapping
    static PARENS = values().collectEntries { entry ->
        [(entry.value): entry]
    }

    static Paren findParen(String value) {
        if (value == null) {
            throw new IllegalArgumentException()
        } else {
            PARENS[(value)] as Paren
        }
    }

    static Boolean consistsOfParensOnly(String parens) {
        parens ==~ /[${LEFT.value}${RIGHT.value})]*/
    }
}

