package misc

import groovy.util.logging.Slf4j
import spock.lang.Narrative
import spock.lang.Specification

@Slf4j
@Narrative("HashCode based on https://reflectoring.io/feature-flags-make-or-buy")
class HashCodeSpec extends Specification {

    def "should calculate hash code (#expected) from ('#source')"() {
        expect:
        expected == HashCode.percentageHashCode(source)
        where:
        source        || expected
        ''            || 19.888289319351315
        'x'           || 34.752180287614465
        'xyz'         || 22.72202940657735
        'ala ma kota' || 82.55872335284948
    }
}