package utils

import com.browserup.bup.BrowserUpProxy
import groovy.transform.Canonical
import groovy.util.logging.Slf4j

@Slf4j
@Canonical
class CustomizedWebDriver {
    Boolean headless
    Boolean shareLocation
    BrowserUpProxy proxy

    //TODO - Rethink approach here...
}
