package parameterize

import groovy.transform.Canonical
import groovy.util.logging.Slf4j

@Slf4j
@Canonical
class Configuration {
    String id
    String label
}
