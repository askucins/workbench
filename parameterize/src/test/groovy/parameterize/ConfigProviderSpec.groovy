package parameterize

import groovy.util.logging.Slf4j
import spock.lang.Specification

@Slf4j
class ConfigProviderSpec extends Specification {
    def setupSpec() {
        ConfigProvider.reset()
    }
    def "should return configurations"() {
        expect:
        ConfigProvider.env.dev == ConfigProvider.next()
        and:
        ConfigProvider.env.test == ConfigProvider.next()
        and:
        ConfigProvider.env.prod == ConfigProvider.next()
        when:
        ConfigProvider.next()
        then:
        thrown(NoSuchElementException)
    }
}
