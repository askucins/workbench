package misc.traversing

import spock.lang.Shared
import spock.lang.Specification

abstract class TraverseTreeSpec extends Specification {

    @Shared
    TreeWalker treeWalker

    def "should find all small letters between 'c' and 'g'"() {
        given:
        def tree = [
                a: [
                        b: [
                                p: [:],
                                f: [:]
                        ],
                        c: [
                                C: [:],
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
        def processor = new FilteredAccumulator(filter: { it ==~ /[cdefg]/ })
        when:
        treeWalker.processTree(tree, processor)
        then:
        processor.accu.sort() == ['c', 'e', 'f', 'g', 'g']
    }

    def "should return all nodes when filter accepts all of them"() {
        given:
        def tree = [a: [b: [:], c: [d: [:]]]]
        def processor = new FilteredAccumulator(filter: { true })
        when:
        treeWalker.processTree(tree, processor)
        then:
        processor.accu.sort() == ['a', 'b', 'c', 'd']
    }

    def "should not find anything in an empty tree"() {
        given:
        def tree = [:]
        def processor = new FilteredAccumulator(filter: { true })
        when:
        treeWalker.processTree(tree, processor)
        then:
        processor.accu.sort() == []
    }

    def "should not find anything in a null tree"() {
        given:
        def tree = null
        def processor = new FilteredAccumulator(filter: { true })
        when:
        treeWalker.processTree(tree, processor)
        then:
        processor.accu.sort() == []
    }

    def "should not find anything when provided filter does not match to any node"() {
        given:
        def tree = [a: [b: [:], c: [d: [:]]]]
        def processor = new FilteredAccumulator(filter: { false })
        when:
        treeWalker.processTree(tree, processor)
        then:
        processor.accu.sort() == []
    }
}