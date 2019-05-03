package misc.traversing

import spock.lang.Specification

class TraverseTreeSpec extends Specification {

    def "should find all small letters between 'c' and 'g'"() {
        given:
        def tree = [
                a: [
                        b: [
                                p: [:],
                                f: [:]
                        ],
                        c: [
                                c: [:],
                                E: [
                                        q: [:],
                                        e: [:]
                                ]
                        ],
                        D: [
                                g: [
                                        g: [:]
                                ]
                        ]
                ]
        ]
        def processor = new Processor(filter: { it ==~ /[cdefg]/ })
        when:
        TraverseTree.processTree(tree, processor)
        then:
        processor.accu.sort() == ['c', 'c', 'e', 'f', 'g', 'g']
    }

    def "should not find all small letters between 'c' and 'g' in an empty tree"() {
        given:
        def tree = [:]
        def processor = new Processor(filter: { it ==~ /[cdefg]/ })
        when:
        TraverseTree.processTree(tree, processor)
        then:
        processor.accu.sort() == []
    }

    def "should not find all small letters between 'c' and 'g' in a null tree"() {
        given:
        def tree = null
        def processor = new Processor(filter: { it ==~ /[cdefg]/ })
        when:
        TraverseTree.processTree(tree, processor)
        then:
        processor.accu.sort() == []
    }
}