package misc.traversing

import groovy.util.logging.Slf4j

// Based on https://stackoverflow.com/questions/38239418/tail-recursive-tree-traversal-without-loops

@Slf4j
class TreeWalkerTailRecursive implements TreeWalker {

    private static List dfs(List stack, TreeNode head) {
        log.debug "dfs stack: {}", stack as String
        log.debug "dfs head: {}", head as String
        head.children + stack
    }

    private loop(FilteredAccumulator accumulator, List stack) {
        log.debug "Stack: {}", stack as String
        if (stack.size() > 0) {
            TreeNode head = stack.pop()
            log.debug "Head: {}", head as String
            List tail = stack
            log.debug "Tail: {}", tail as String
            if (head && !head.isEmpty()) {
                accumulator.consume(head.value)
                log.debug "Accu: {}", accumulator.accu
                return loop(accumulator, dfs(tail, head))
            }
        }
    }

    def processTree(TreeNode tree, FilteredAccumulator accumulator) {
        loop(accumulator, [tree])
    }
}
