package misc.leftfold

class LeftFolder {
    static Collection upperCaser(Collection input) {
        input.inject([]) { output, elem ->
            output << elem.toString().toUpperCase()
        }
    }

    static String concatenater(Collection input) {
        input.inject('') { output, elem ->
            output << elem.toString()
        }
    }
}
