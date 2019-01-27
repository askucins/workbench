package misc.matching

import groovy.util.logging.Slf4j
import spock.lang.Shared
import spock.lang.Specification

import java.util.regex.Matcher

// Based on http://pleac.sourceforge.net/pleac_groovy/patternmatching.html

@Slf4j
class MatchMeSpec extends Specification {

    @Shared
    String meadow1, meadow2

    def setupSpec() {
        meadow1 = 'cow grass butterflies Ovine'
        meadow2 = 'goat sheep flowers dog'
    }

    def "should match parens"() {
        given:
        String parens = '()'
        when:
        def subparens = parens =~ /^\((.*)\)$/
        then:
        subparens
        cleanup:
        log.info "|{}|", subparens
    }

    def "should regex pattern operator work with find"() {
        when:
        def partial = /sheep/
        then: "find operator"
        !(meadow1 =~ partial)
        and:
        meadow2 =~ partial

        when:
        def finder = (meadow2 =~ partial)
        then:
        finder instanceof Matcher
    }

    def "should regex pattern operator work match"() {
        when:
        def full = /.*sheep.*/
        then: "match operator"
        !(meadow1 ==~ full)
        and:
        meadow2 ==~ full

        when:
        def matcher = (meadow2 ==~ full)
        then:
        matcher instanceof Boolean
    }

    def "should matching be case insensitive"() {
        expect:
        meadow1 =~ /(?i)\bovines?\b/
    }

    def "should match in that sneaky case"() {
        given:
        def stuff = 'ababacaca'
        when:
        def m = stuff =~ /(a|ba|b)+(a|ac)+/
        then:
        m.matches() && m.lookingAt() && !m.find()
        and:
        m.groupCount() == 2
        and:
        m[0][0] == 'ababa' && m.group(0) == 'ababa'
        and:
        m[0][1] == 'b' && m.group(1) == 'b' && stuff.substring(m.start(1), m.end(1)) == 'b'
        and:
        m[0][2] == 'a' && m.group(2) == 'a' && stuff.substring(m.start(2), m.end(2)) == 'a'
        and:
        m.pattern().toString() == '(a|ba|b)+(a|ac)+'
        and:
        m.regionStart() == 0
        and:
        m.regionEnd() == 9

        cleanup:
        [0, 1, 2].each { group ->
            log.debug "Group: {}, Start:{}, End:{}, Substring:{}", group, m.start(group), m.end(group), stuff.substring(m.start(group), m.end(group))
        }
    }

    def "should match in that sneaky case resolved"() {
        given:
        def stuff = 'ababacaca'
        when:
        def m = stuff =~ /(a|ba|b)(a|ba|b)(a|ba|b)(a|ac)/
        then:
        !m.matches() && m.lookingAt() && !m.find()
        and:
        m.groupCount() == 4
        and:
        m[0][0] == 'ababa' && m.group(0) == 'ababa'
        and:
        m[0][1] == 'a' && m.group(1) == 'a' && stuff.substring(m.start(1), m.end(1)) == 'a'
        and:
        m[0][2] == 'ba' && m.group(2) == 'ba' && stuff.substring(m.start(2), m.end(2)) == 'ba'
        and:
        m[0][3] == 'b' && m.group(3) == 'b' && stuff.substring(m.start(3), m.end(3)) == 'b'
        and:
        m[0][4] == 'a' && m.group(4) == 'a' && stuff.substring(m.start(4), m.end(4)) == 'a'
        and:
        m.pattern().toString() == '(a|ba|b)(a|ba|b)(a|ba|b)(a|ac)'
        and:
        m.regionStart() == 0
        and:
        m.regionEnd() == 9

        cleanup:
        [0, 1, 2, 3, 4].each { group ->
            log.debug "Group: {}, Start:{}, End:{}, Substring:{}", group, m.start(group), m.end(group), stuff.substring(m.start(group), m.end(group))
        }
    }
}