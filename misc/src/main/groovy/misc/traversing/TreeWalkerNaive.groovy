package misc.traversing

import groovy.util.logging.Slf4j

@Slf4j
class TreeWalkerNaive implements TreeWalker {

    def processTree(TreeNode tree, FilteredAccumulator accumulator) {
        log.debug "Tree: {}", tree as String
        if (tree && !tree.isEmpty()) {
            def head = tree.value
            log.debug "Head: {}", head as String
            def tail = tree.children
            log.debug "Tail: {}", tail as String
            accumulator.consume(head)
            log.debug "Accu: {}", accumulator.accu as String
            tail?.each { processTree(it as TreeNode, accumulator) }
        }
    }
}
