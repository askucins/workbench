package parameterize

import groovy.util.logging.Slf4j

@Slf4j
class ParameterizeByInheritanceCaseDevSpec extends ParameterizeSpec {
    def setupSpec() {
        configuration = ConfigurationProvider.env.dev
    }
}
