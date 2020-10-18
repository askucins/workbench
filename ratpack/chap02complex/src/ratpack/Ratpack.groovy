import static ratpack.groovy.Groovy.ratpack

import chap02complex.DefaultRouteHandler

ratpack { 
  bindings {
    add(new DefaultRouteHandler("Hello, world!"))
  }
  handlers {
    get(DefaultRouteHandler)
    get("hello", DefaultRouteHandler)
  } 
}



