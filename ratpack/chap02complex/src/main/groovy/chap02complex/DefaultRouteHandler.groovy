package chap02complex

import groovy.transform.Canonical
import ratpack.handling.Handler
import ratpack.handling.Context

@Canonical
class DefaultRouteHandler implements Handler {
    String message

    @Override
    void handle (Context context) {
        context.render message
    }
}

