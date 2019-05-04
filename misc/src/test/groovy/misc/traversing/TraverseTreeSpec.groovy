package misc.traversing

import spock.lang.Shared
import spock.lang.Specification

abstract class TraverseTreeSpec extends Specification {

    @Shared
    TreeWalker treeWalker

    def "should not find anything in a null tree"() {
        given:
        def tree = null
        def processor = new FilteredAccumulator(filter: { true })
        when:
        treeWalker.processTree(tree, processor)
        then:
        processor.accu.sort() == []
    }

    def "should not find anything in an empty tree"() {
        given:
        def tree = new TreeNode()
        def processor = new FilteredAccumulator(filter: { true })
        when:
        treeWalker.processTree(tree, processor)
        then:
        processor.accu.sort() == []
    }

    def "should not find anything when provided filter does not match to any node"() {
        given:
        def tree = new TreeNode(value: 'a',
                children: [
                        new TreeNode(value: 'b'), new TreeNode(value: 'c',
                        children: [
                                new TreeNode(value: 'd')])])
        def processor = new FilteredAccumulator(filter: { false })
        when:
        treeWalker.processTree(tree, processor)
        then:
        processor.accu.sort() == []
    }

    def "should return a matching node"() {
        given:
        def tree = new TreeNode(value: 'a')
        def processor = new FilteredAccumulator(filter: { it == 'a' })
        when:
        treeWalker.processTree(tree, processor)
        then:
        processor.accu.sort() == ['a']
    }

    def "should return a match even when traversing via null values"() {
        given:
        def tree = new TreeNode(value: null,
                children: [
                        new TreeNode(value: 'b'), new TreeNode(value: 'c',
                        children: [
                                new TreeNode(value: null)])])
        def processor = new FilteredAccumulator(filter: { true })
        when:
        treeWalker.processTree(tree, processor)
        then:
        processor.accu.sort() == ['b', 'c']
    }

    def "should return all nodes when filter accepts all of them"() {
        given:
        def tree = new TreeNode(value: 'a',
                children: [
                        new TreeNode(value: 'b'), new TreeNode(value: 'c',
                        children: [
                                new TreeNode(value: 'd')])])
        def processor = new FilteredAccumulator(filter: { true })
        when:
        treeWalker.processTree(tree, processor)
        then:
        processor.accu.sort() == ['a', 'b', 'c', 'd']
    }

    def "should find all small letters between 'c' and 'g'"() {
        given:
        def tree =
                new TreeNode(value: 'a',
                        children: [
                                new TreeNode(value: 'b',
                                        children: [
                                                new TreeNode(value: 'p'),
                                                new TreeNode(value: 'f', children: [
                                                        new TreeNode(value: 'g')])]),
                                new TreeNode(value: 'c',
                                        children: [
                                                new TreeNode(value: 'C'),
                                                new TreeNode(value: 'E',
                                                        children: [
                                                                new TreeNode(value: 'q'),
                                                                new TreeNode(value: 'e')])]),
                                new TreeNode(value: 'D',
                                        children: [
                                                new TreeNode(value: 'g',
                                                        children: [
                                                                new TreeNode(value: 'g')])])])
        def processor = new FilteredAccumulator(filter: { it ==~ /[cdefg]/ })
        when:
        treeWalker.processTree(tree, processor)
        then:
        processor.accu.sort() == ['c', 'e', 'f', 'g', 'g', 'g']
    }
}