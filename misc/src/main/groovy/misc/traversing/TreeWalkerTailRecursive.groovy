package misc.traversing

import groovy.util.logging.Slf4j

@Slf4j
class TreeWalkerTailRecursive implements TreeWalker {

    private static List dfs(List stack, Map head) {
        log.debug "dfs stack: {}", stack as String
        log.debug "dfs head: {}", head as String
        log.debug "dfs values: {}", head?.values() as String
        head?.values()?.toList() == [[:]] ? stack : head?.values() + stack
    }

    private loop(FilteredAccumulator accumulator, List stack) {
        log.debug "Stack: {}", stack as String
        if (stack.size() > 0) {
            Map head = stack.pop()
            log.debug "Head: {}", head as String
            List tail = stack
            log.debug "Tail: {}", tail as String
            accumulator.consume(head?.keySet() ? head?.keySet()?.first() : null)
            log.debug "Accu: {}", accumulator.accu
            return loop(accumulator, dfs(tail, head))
        }
    }

    def processTree(Map tree, FilteredAccumulator accumulator) {
        loop(accumulator, [tree])
    }
}
