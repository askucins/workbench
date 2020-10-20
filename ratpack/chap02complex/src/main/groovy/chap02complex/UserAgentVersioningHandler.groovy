package chap02complex

import groovy.transform.Canonical
import groovy.util.logging.Slf4j
import ratpack.handling.Context 
import ratpack.handling.Handler 
import ratpack.registry.Registry 
import static ratpack.jackson.Jackson.json

@Slf4j
@Canonical
class UserAgentVersioningHandler implements Handler {
    private static String ERROR_MSG = "Unsupported User Agent!"
    
    @Override
    void handle(Context context) {
        def userAgent = context.request.headers.get("User-Agent")
        log.info "User Agent: {}", userAgent
        ClientVersion clientVersion = ClientVersion.fromString(userAgent)
        log.info "CV: {}", clientVersion
        if (!clientVersion) {
            renderError(context)
        } else {
            context.next(Registry.single(ClientVersion, clientVersion))
        }
    }

    private static void renderError(Context context) {
        context.response.status(400)
        context.byContent { spec -> 
            spec.json({
                context.render(json([error: true, message: ERROR_MSG]))
            }).html({
                context.render("<h1>400 Bad Request</h1><br/><div>${ERROR_MSG}</div>")
            }).noMatch {context.render(ERROR_MSG)}    
        }
    }
}
