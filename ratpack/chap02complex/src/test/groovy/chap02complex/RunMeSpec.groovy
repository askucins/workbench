package chap02complex

import spock.lang.Specification

class RunMeSpec extends Specification {

    def "should find given client version"() {
        given:
        def clientVersion = UserAgentVersioningHandler.ClientVersion.V1
        expect:
        UserAgentVersioningHandler.ClientVersion.fromString(clientVersion.version) == clientVersion 
    }
}
