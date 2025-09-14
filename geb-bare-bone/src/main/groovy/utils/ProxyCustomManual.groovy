package utils

import groovy.transform.Canonical
import groovy.transform.ToString
import groovy.util.logging.Slf4j
import org.openqa.selenium.Proxy

@Slf4j
@Canonical
@ToString(includeNames = true, includePackage = false, includeSuper = true)
class ProxyCustomManual extends ProxyCustom {

    ProxyCustomManual() {
        proxy = new Proxy().tap {
            it.proxyType = Proxy.ProxyType.MANUAL
            it.httpProxy = "localhost:7979"
            it.sslProxy = "localhost:7979"
        }
        this
    }

    void stop() {
        log.info "Manual proxy will not be stopped!"
    }
}
