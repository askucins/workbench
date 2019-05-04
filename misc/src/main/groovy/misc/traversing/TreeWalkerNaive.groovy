package misc.traversing

import groovy.util.logging.Slf4j

@Slf4j
class TreeWalkerNaive implements TreeWalker {

    def processTree(Map tree, FilteredAccumulator accumulator) {
        log.debug "Tree: {}", tree as String
        def head = tree?.keySet() ? tree?.keySet()?.first() : null
        log.debug "Head: {}", head as String
        if (head != null) {
            def tail = tree[(head)]
            log.debug "Tail: {}", tail as String
            accumulator.consume(head)
            log.debug "Accu: {}", accumulator.accu as String
            tail?.keySet()?.each { processTree([(it): tail[(it)]] as Map, accumulator) }
        }
    }
}
