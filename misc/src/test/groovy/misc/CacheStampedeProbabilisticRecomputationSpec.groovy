package misc

import groovy.util.logging.Slf4j
import spock.lang.Narrative
import spock.lang.See
import spock.lang.Specification


@Narrative(""")
    This specification explores the concept of cache stampede and the probabilistic recomputation strategy to mitigate it.
    A cache stampede occurs when multiple requests simultaneously attempt to recompute an expired cache entry, leading to
    excessive load on the backend system. Probabilistic recomputation helps to spread out these requests over time,
    reducing the likelihood of a stampede.
""")
@See('https://medium.com/@srilakshmigeetha/cache-me-if-you-can-the-cache-stampede-behind-facebooks-outage-part-3-ca28627ce871')
@See('https://en.wikipedia.org/wiki/Cache_stampede#Probabilistic_early_expiration')
@Slf4j
class CacheStampedeProbabilisticRecomputationSpec extends Specification {

    static Long numberOfAttempts = 20

    /**
     * Calculates the recomputation time based on an exponential distribution.
     * See: https://en.wikipedia.org/wiki/Cache_stampede#Probabilistic_early_expiration
     * @param delta : time to refresh the resource (from the database or source)
     * @param beta = 1.0 : but set to a value greater than one to favor early recomputation
     * @return
     */
    Long calculateRecomputationTime(Long delta, Double beta) {
        Double u = Math.random() // Uniform random number between 0 and 1
        Double recomputationTime = -delta * beta * Math.log(u)
        recomputationTime.longValue()
    }

    Boolean shouldRecomputeCacheEntry(Long currentTime, Long expirationTime, Long recomputationTime) {
        currentTime + recomputationTime >= expirationTime
    }

    def "should calculate recomputation probability (attempt #attempt of #attempts)"() {
        given: "a cache entry with a specific expiration time and current time (time in seconds, like epoch time)"
        Long currentTime = 50L
        Long expirationTime = 60L
        and: "recomputation parameters"
        Long delta = 10L //Time to fetch the resource from the database. (Can also use the last recomputed time)
        Double beta = 2.0 //Default 1.0 : but set to a value greater than one to favor early recomputation
        when:
        Long recomputationTime = calculateRecomputationTime(delta, beta)
        then: "the recomputation probability to be calculated correctly"
        recomputationTime >= 0
        cleanup:
        log.info "Delta: {}, Lambda: {}, CT: {}, ET: {}, RT: {}, should refresh: {}", delta, beta, currentTime, expirationTime, recomputationTime, shouldRecomputeCacheEntry(currentTime, expirationTime, recomputationTime)
        where:
        attempts = numberOfAttempts
        attempt << (1..numberOfAttempts)
    }
}
