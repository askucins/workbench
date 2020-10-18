import static ratpack.groovy.Groovy.ratpack

import chap02complex.DefaultRouteHandler
import chap02complex.UserAgentVersioningHandler

ratpack {
  handlers {
    get(new UserAgentVersioningHandler())

    get("api") { UserAgentVersioningHandler.ClientVersion clientVersion ->
      if (clientVersion == UserAgentVersioningHandler.ClientVersion.V1) {
        render "V1 Model"
      } else if (clientVersion == UserAgentVersioningHandler.ClientVersion.V2) {
        render "V2 Model"
      } else {             // it must be V3 at this point, as the versioning        
        render "V3 Model"  // handler has figured out the request qualifies
      }
    }
  }
}


