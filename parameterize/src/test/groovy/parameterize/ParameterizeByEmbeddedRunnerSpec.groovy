package parameterize

import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Unroll
import spock.util.EmbeddedSpecRunner
import spock.util.mop.ConfineMetaClassChanges

@Slf4j
class ParameterizeByEmbeddedRunnerSpec extends Specification {

    @Unroll
    @ConfineMetaClassChanges([ParameterizeSpec])
    def "should validation for (#instance) pass"() {
        given:
        ParameterizeSpec.metaClass.getConfig = { config }
        def runner = new EmbeddedSpecRunner(throwFailure: false)
        ConfigProvider.reset()
        when:
        EmbeddedSpecRunner.SummarizedEngineExecutionResults result = runner.runClass(ParameterizeSpec)
        then:
        result.totalFailureCount == expectedFailureCount

        where:
        instance | config                  || expectedFailureCount
        'PROD'   | ConfigProvider.env.prod || 0
        'TEST'   | ConfigProvider.env.test || 0
        'DEV'    | ConfigProvider.env.dev  || 0
    }
}

//TODO this does not work as expected - errors from the called specs are somehow omitted...