package grokalg

import spock.lang.Specification

class IntroSpec extends Specification {
    def "should intro work"() {
        expect:
        Intro.intro() == 'Hello, world!'
    }

    def "should not non-intro work"() {
        expect:
        Intro.intro() == 'Hello, alamakota!'
    }
}
