package misc.traversing

import groovy.transform.Canonical

@Canonical
class FilteredAccumulator {
    List accu = []
    Closure filter

    def consume(def candidate) {
        if (candidate != null && filter(candidate)) {
            accu.add(candidate)
        }
    }
}