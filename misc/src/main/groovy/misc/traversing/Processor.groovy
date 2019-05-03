package misc.traversing

import groovy.transform.Canonical

@Canonical
class Processor {
    List accu = []
    Closure filter

    def validate(def candidate) {
        if (filter(candidate)) {
            accu.add(candidate)
        }
    }
}