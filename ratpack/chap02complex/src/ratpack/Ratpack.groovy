import static ratpack.groovy.Groovy.ratpack
import org.slf4j.LoggerFactory
import chap02complex.UserAgentVersioningHandler
import chap02complex.ClientVersion

def logger = LoggerFactory.getLogger(this.class)

ratpack {
  handlers {
    all(new UserAgentVersioningHandler())

    get("foo") {
      logger.info "User Agent: {}", request.headers.get("User-Agent")
      render "bar of ${ClientVersion.V1}:${ClientVersion.V1.version}"
    }

    get("api") { ClientVersion clientVersion ->
      logger.info "CV: {}", clientVersion
      if (clientVersion == ClientVersion.V1) {
        render "V1 Model"
      } else if (clientVersion == ClientVersion.V2) {
        render "V2 Model"
      } else {             // it must be V3 at this point, as the versioning        
        render "V3 Model"  // handler has figured out the request qualifies
      }
    }
  }
}


