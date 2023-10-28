package parameterize

import groovy.util.logging.Slf4j

@Slf4j
class ParameterizeByInheritanceCaseProdSpec extends ParameterizeSpec {
    def setupSpec() {
        configuration = ConfigurationProvider.env.prod
    }
}
