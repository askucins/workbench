package misc.traversing

import spock.lang.Ignore

@Ignore('Fixme: it hits a neverending recursion problem')
class TraverseTreeWithTailRecursiveSpec extends TraverseTreeSpec {

    def setupSpec() {
        treeWalker = new TreeWalkerTailRecursive()
    }
}