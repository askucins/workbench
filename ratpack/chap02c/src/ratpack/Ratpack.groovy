import static ratpack.groovy.Groovy.ratpack

import chap02c.DefaultRouteHandler

ratpack { 
  bindings {
    add(new DefaultRouteHandler("Hello, world!"))
  }
  handlers {
    get(DefaultRouteHandler)
  } 
}



