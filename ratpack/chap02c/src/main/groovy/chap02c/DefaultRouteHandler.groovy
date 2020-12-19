package chap02c

import groovy.transform.Canonical
import ratpack.handling.Context
import ratpack.handling.Handler

@Canonical
class DefaultRouteHandler implements Handler {
    String message

    @Override
    void handle(Context context) {
        context.render message
    }
}

