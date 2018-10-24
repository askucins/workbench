package misc.process

import spock.lang.Specification


class ProcessDemoExecSpec extends Specification {

    def "should demo run"() {
        expect:
        ProcessDemoExec.main()
    }
}