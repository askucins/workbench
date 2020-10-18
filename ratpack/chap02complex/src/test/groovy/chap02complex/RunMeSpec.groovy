package chap02complex

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class RunMeSpec extends Specification {

    def "should find client version of (#version)"() {
        expect:
        UserAgentVersioningHandler.ClientVersion.fromString(version) == clientVersion 
        where:
        version                    | clientVersion
        "Microservice Client v1.0" | UserAgentVersioningHandler.ClientVersion.V1
        "Microservice Client v2.0" | UserAgentVersioningHandler.ClientVersion.V2
        "Microservice Client v3.0" | UserAgentVersioningHandler.ClientVersion.V3
        "Microservice Client"      | null

    }
}
