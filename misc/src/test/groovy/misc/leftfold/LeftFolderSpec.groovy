package misc.leftfold


import spock.lang.Specification

class LeftFolderSpec extends Specification {

    def "should fold via upperCaser"() {
        when:
        Collection result = LeftFolder.upperCaser(["aa", "bb"])

        then:
        result == ["AA", "BB"]
    }

    def "should fold via concatenater"() {
        when:
        String result = LeftFolder.concatenater(["aa", "bb"])

        then:
        result == "aabb"
    }
}
