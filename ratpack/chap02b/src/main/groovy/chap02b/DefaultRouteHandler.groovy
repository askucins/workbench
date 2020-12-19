package chap02b

import groovy.transform.Canonical
import ratpack.handling.Context
import ratpack.handling.Handler

@Canonical
class DefaultRouteHandler implements Handler {
    String defaultMessage

    @Override
    void handle(Context context) {
        if (context.pathTokens.containsKey('name')) {
            context.render "Hello, ${context.pathTokens.name}!"
        } else {
            context.render defaultMessage
        }
    }
}

