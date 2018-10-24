package misc.process

import misc.process.ProcessDemoMemory
import spock.lang.Specification


class ProcessDemoMemorySpec extends Specification {

    def "should demo run"() {
        expect:
        ProcessDemoMemory.main()
    }
}