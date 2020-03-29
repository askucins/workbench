package parameterize

import groovy.util.logging.Slf4j
import org.junit.BeforeClass
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith

@RunWith(Enclosed)
@Slf4j
class ParameterizeByTestSuiteSpec {

    @BeforeClass
    static void resetConfig() {
        ConfigProvider.reset()
    }

    static class RunInProdSpec extends ParameterizeSpec {
        def setupSpec() {
            config = ConfigProvider.env.prod
        }
    }

    static class Run01Spec extends ParameterizeSpec {
        def setupSpec() {
            config = ConfigProvider.next()
        }
    }

    static class Run02Spec extends ParameterizeSpec {
        def setupSpec() {
            config = ConfigProvider.next()
        }
    }

    // TODO what would be nice is to have that list of classes generated on the fly though...
    // FIXME this stopped working after switching to groovy3 (and related spock and junit platform)
}
