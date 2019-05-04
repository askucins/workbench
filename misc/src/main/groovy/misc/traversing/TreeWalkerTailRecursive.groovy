package misc.traversing

class TreeWalkerTailRecursive implements TreeWalker {

    private static List dfs(List _stack, Map _head) {
        _head.entrySet() + _stack
    }

    private loop(FilteredAccumulator accumulator, List stack) {
        if (stack) {
            Map head = stack.pop()
            List tail = stack
            accumulator.consume(head.keySet().first())
            return loop(accumulator, dfs(tail, head))
        }
    }

    def processTree(Map tree, FilteredAccumulator accumulator) {
        loop(accumulator, [tree])
    }
}
