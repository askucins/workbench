package parameterize

import groovy.transform.Canonical
import groovy.util.logging.Slf4j

@Slf4j
@Canonical
class Config {
    String id
    String label
}
