package chap02complex

import groovy.transform.Canonical
import ratpack.handling.Handler
import ratpack.handling.Context

@Canonical
class DefaultRouteHandler implements Handler {
    String defaultMessage

    @Override
    void handle (Context context) {
        if (context.pathTokens.containsKey("name")) {
            context.render "Hello, ${context.pathTokens.name}!"       
        } else {
            context.render defaultMessage
        }
    }
}

