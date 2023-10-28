package parameterize

import groovy.util.logging.Slf4j
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested

@Slf4j
class ParameterizeByTestSuite {

//    @BeforeEach
//    static void resetConfig() {
//        ConfigurationProvider.reset()
//    }
//
//    @Nested
//    class RunInProdSpec extends ParameterizeSpec {
//        def setupSpec() {
//            config = ConfigurationProvider.env.prod
//        }
//    }
//
//    @Nested
//    class Run01Spec extends ParameterizeSpec {
//        def setupSpec() {
//            config = ConfigurationProvider.next()
//        }
//    }
//
//    @Nested
//    class Run02Spec extends ParameterizeSpec {
//        def setupSpec() {
//            config = ConfigurationProvider.next()
//        }
//    }

    // TODO what would be nice is to have that list of classes generated on the fly though...
    // FIXME this stopped working after switching to groovy3 (and related spock and junit platform)
}
