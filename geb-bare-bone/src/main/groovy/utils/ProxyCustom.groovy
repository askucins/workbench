package utils

import groovy.transform.Canonical
import groovy.transform.ToString
import groovy.util.logging.Slf4j
import org.openqa.selenium.Proxy

@Slf4j
@Canonical
@ToString(includeNames = true, includePackage = false)
abstract class ProxyCustom {

    Proxy proxy

    Proxy proxy() {
        this.proxy
    }

    void stop() {}
}