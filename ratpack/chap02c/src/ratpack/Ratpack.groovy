import chap02c.DefaultRouteHandler

import static ratpack.groovy.Groovy.ratpack

ratpack {
    bindings {
        add(new DefaultRouteHandler("Hello, world!"))
    }
    handlers {
        get(DefaultRouteHandler)
    }
}



