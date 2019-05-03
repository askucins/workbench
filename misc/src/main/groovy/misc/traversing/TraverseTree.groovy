package misc.traversing

import groovy.util.logging.Slf4j

@Slf4j
class TraverseTree {
    static processTree(Map tree, Processor processor) {
        log.debug "Tree: {}", tree as String
        def head = tree?.keySet() ? tree?.keySet()?.first() : null
        log.debug "Head: {}", head as String
        if (head != null) {
            def tail = tree[(head)]
            log.debug "Tail: {}", tail as String
            processor.validate(head)
            log.debug "Accu: {}", processor.accu as String
            tail?.keySet()?.each { processTree([(it): tail[(it)]] as Map, processor) }
        }
    }
}
