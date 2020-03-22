package parameterize

import groovy.util.logging.Slf4j

@Slf4j
class RunMeProdSpec extends ParameterizeSpec {
    def setupSpec() {
        config = ConfigProvider.env.prod
    }
}
