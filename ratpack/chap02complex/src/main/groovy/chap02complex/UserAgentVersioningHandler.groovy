package chap02complex

import groovy.transform.Canonical
import ratpack.handling.Context 
import ratpack.handling.Handler 
import ratpack.registry.Registry 
import static ratpack.jackson.Jackson.json

@Canonical
class UserAgentVersioningHandler implements Handler {
    private static String ERROR_MSG = "Unsupported User Agent!"
    
    enum ClientVersion {
        V1("Microservice Client v1.0"),
        V2("Microservice Client v2.0"),
        V3("Microservice Client v3.0")

        String version

        ClientVersion(String version) {
            this.version = version
        }

        static ClientVersion fromString(String val) {
            values().find {it.version == val } ?: null
        }
    }

    @Override
    void handle(Context context) {
        def userAgent = context.request.headers.get("User-Agent")
        def clientVersion = ClientVersion.fromString(userAgent)
        if (!clientVersion) {
            renderError(context)
        } else {
            context.next(Registry.single(ClientVersion, clientVersion))
        }
    }

    private static void renderError(Context context) {
        context.response.status(400)
        context.byContent { spec -> spec
            .json({context.render(json([error: true, message: ERROR_MSG]))})
            .html({context.render("<h1>400 Bad Request</h1><br/><div>${ERROR_MSG}</div>")})
            .noMatch {context.render(ERROR_MSG)}    
        }
    }
}
