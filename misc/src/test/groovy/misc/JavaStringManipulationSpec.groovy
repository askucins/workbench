package misc

import groovy.util.logging.Slf4j
import spock.lang.Narrative
import spock.lang.Specification

import java.text.MessageFormat

import static java.text.MessageFormat.format
import static java.util.stream.Collectors.joining

@Narrative("See more at https://andbin.dev/java/string-building-and-formatting")
@Slf4j
class JavaStringManipulationSpec extends Specification {

    def "should manipulate string with Groovy plus operator"() {
        given:
        def str = "Hello"
        when:
        str = str + " World " + 2025 + "!"
        then:
        str == "Hello World 2025!"
    }

    def "should manipulate string with Groovy GString"() {
        given:
        def year = 2025
        def str = "Hello"
        when:
        str = "${str} World ${year}!"
        then:
        str == "Hello World 2025!"
    }

    def "should manipulate string with Java StringBuffer"() {
        //It works, but StringBuilder is preferred in single-threaded scenarios
        given:
        def stringBuffer = new StringBuffer("Hello")
        when:
        stringBuffer.append(" World ").append(2025).append("!")
        then:
        stringBuffer.toString() == "Hello World 2025!"
    }

    def "should manipulate string with Java StringBuilder"() {
        given:
        def stringBuilder = new StringBuilder("Hello")
        when:
        stringBuilder.append(" World ").append(2025).append("!")
        then:
        stringBuilder.toString() == "Hello World 2025!"
    }

    def "should manipulate string with Java string concatenation"() {
        given:
        def str = "Hello"
        when:
        str += " World " + 2025 + "!"
        then:
        str == "Hello World 2025!"
    }

    def "should manipulate string with Java Formatter"() {
        given:
        def str = "Hello"
        def year = 2025
        def stringBuilder
        when:
        //try-with-resources to auto-close the Formatter
        try (Formatter fmt = new Formatter()) {
            fmt.format("%s World %d!", str, year)
            stringBuilder = fmt.out()
        }
        then:
        stringBuilder.toString() == "Hello World 2025!"
    }

    def "should manipulate string with Java Formatter and StringBuilder"() {
        given:
        def str = "Hello"
        def year = 2025
        def stringBuilder = new StringBuilder()
        when:
        //Formatter can write to any Appendable object!
        try (Formatter fmt = new Formatter(stringBuilder)) {
            fmt.format("%s World %d!", str, year)
        }
        then:
        stringBuilder.toString() == "Hello World 2025!"
    }

    def "should manipulate string with Java Formatter and StringBuilder (short)"() {
        given:
        def str = "Hello"
        def year = 2025
        when:
        // Formatter implicitly uses a StringBuilder
        def s = new Formatter().format("%s World %d!", str, year).toString()
        then:
        s == "Hello World 2025!"
    }

    def "should manipulate string with Java string format"() {
        //Like in C's printf :)
        given:
        def str = "Hello"
        when:
        str = String.format("%s World %d!", str, 2025)
        then:
        str == "Hello World 2025!"
    }

    def "should manipulate string with Java string formatted"() {
        //A variant of String.format() since Java 15
        given:
        def str = "Hello"
        when:
        str = '%s World %d!'.formatted(str, 2025)
        then:
        str == "Hello World 2025!"
    }

    def "should manipulate string with Java MessageFormat"() {
        //More powerful formatting, especially for i18n
        given:
        def str = "Hello"
        when:
        str = format("{0} World {1}!", str, 2025)
        then:
        str == "Hello World 2,025!" //Note the locale-specific formatting of the number!! (en_US by default)
    }

    def "should manipulate string with Java MessageFormat and Locale"() {
        //More powerful formatting, especially for i18n
        given:
        Locale defaultLocale = Locale.getDefault()
        log.info "Current locale: {}", Locale.getDefault()
        Locale.setDefault(new Locale('pl', 'PL'))
        log.info "Current locale: {}", Locale.getDefault()
        and:
        def str = "Hello"
        when:
        str = format("{0} World {1}!", str, 2025)
        then:
        str == "Hello World 2\u00A0025!" //Non-breaking space in Polish formatting
        and:
        !(str ==~ /Hello World 2 025!/) //Regular space does not match
        and:
        !(str ==~ /Hello World 2\s025!/) //\s matches any regular whitespace character
        and:
        //See: https://stackoverflow.com/questions/1060570/why-is-non-breaking-space-not-a-whitespace-character-in-java/6255512
        str ==~ /Hello World 2\p{javaSpaceChar}025!/ //\p{javaSpaceChar} matches any Unicode whitespace character, including non-breaking space
        cleanup:
        //str.each { log.info "'{}' -> {}", it, (int) it }
        str.each { log.info "'{}' -> {}", it, it.codePointAt(0) }
        Locale.setDefault(defaultLocale) //Restore default locale
        log.info "Current locale: {}", Locale.getDefault()
    }

    def "should manipulate string with Java MessageFormat and ChoiceFormat"() {
        /*
            First parameter (0) handles a choice
            then there are 3 choices separated by |

            0#no notifications means “no notifications” when the value is 0
            1#1 notification means “1 notification” when the value is 1
            1<{0,number,integer} notifications} means “n notifications” when n > 1
         */
        given:
        String msg = 'You have {0,choice,0#no notifications|1#1 notification|1<{0,number,integer} notifications}.'
        MessageFormat mf = new MessageFormat(msg)
        expect:
        mf.format([notifications] as Object[]) == result
        where:
        notifications | result
        0             | 'You have no notifications.'
        1             | 'You have 1 notification.'
        4             | 'You have 4 notifications.'
    }

//    @Requires({jvm.isJava21Compatible()})
//    def "should manipulate string with Java String Template"() {
//        /*
//            it requires Java 21+
//            import static java.util.FormatProcessor.FMT
//
//         */
//        given:
//        def user = 'John'
//        def hobbies = ['skiing', 'reading']
//        when:
//        def str = FMT."Hello {user}! You have {hobbies.size()}. Your hobbies are: {hobbies.join(', ')}"
//        then:
//        str == "Hello World 2025!"
//    }

    def "should manipulate string with Java string joining"() {
        given:
        def parts = ["Hello", "World", "2025!"]
        when:
        def str = String.join(" ", parts)
        then:
        str == "Hello World 2025!"
    }

    def "should manipulate string with Java StringJoiner"() {
        given:
        def stringJoiner = new StringJoiner(" ")
        when:
        stringJoiner.add("Hello").add("World").add("2025!")
        then:
        stringJoiner.toString() == "Hello World 2025!"
    }

    def "should manipulate string with Java StringJoiner (fancy version)"() {
        given:
        def stringJoiner = new StringJoiner(" ", "<<", ">>")
        when:
        stringJoiner.add("Hello").add("World").add("2025!")
        then:
        stringJoiner.toString() == "<<Hello World 2025!>>"
    }

    def "should manipulate string with Java Collectors joining"() {
        given:
        def parts = ["Hello", "World", "2025!"]
        when:
        def str = parts.stream().collect(joining(" "))
        then:
        str == "Hello World 2025!"
    }

    def "should manipulate string with Java Collectors joining (fancy version)"() {
        given:
        def parts = ["Hello", "World", "2025!"]
        when:
        def str = parts.stream().collect(joining(" ", "<<", ">>"))
        then:
        str == "<<Hello World 2025!>>"
    }
}
