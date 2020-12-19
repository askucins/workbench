package chap02b

import ratpack.groovy.Groovy
import ratpack.server.RatpackServer

class Main {
    static void main(String... args) {
        RatpackServer.start { spec ->
            spec
                .registryOf({ bnd ->
                    bnd
                        .add(new DefaultRouteHandler("Hello, world!!"))
                })
            //.handlers(Groovy.chain {
            //    get {
            //        render "Hello, world!"
            //    }
            //})
                .handlers(Groovy.chain {
                    get(DefaultRouteHandler)
                    get(":name", DefaultRouteHandler)
                })
        }
    }
}


/*
ratpack { 
	bindings {
		add(new DefaultRouteHandler("Hello, World!"))
	} 
	handlers {
		get(DefaultRouteHandler) 
	} 
}

*/
