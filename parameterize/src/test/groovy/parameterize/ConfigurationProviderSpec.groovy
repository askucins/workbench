package parameterize

import groovy.util.logging.Slf4j
import spock.lang.Specification

@Slf4j
class ConfigurationProviderSpec extends Specification {
    def setupSpec() {
        ConfigurationProvider.reset()
    }

    def "should return configurations"() {
        expect:
        ConfigurationProvider.env.dev == ConfigurationProvider.next()
        and:
        ConfigurationProvider.env.test == ConfigurationProvider.next()
        and:
        ConfigurationProvider.env.prod == ConfigurationProvider.next()
        when:
        ConfigurationProvider.next()
        then:
        thrown(NoSuchElementException)
    }
}
