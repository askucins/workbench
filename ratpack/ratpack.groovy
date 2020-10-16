@Grab('io.ratpack:ratpack-groovy:1.8.0')
@GrabExclude('org.codehaus.groovy:groovy-all')
@Grab('org.slf4j:slf4j-simple:1.7.30')

import static ratpack.groovy.Groovy.ratpack

ratpack {	
	handlers {
		get {
			def name = request.queryParams.who ?: "nobody"
			response.send "Hello, ${name}!"
		}
		get("foo/:id?") {
			def name = pathTokens.id ?: "world"
			response.send "Hello $name"
		}
		path('stuff') {
			byMethod {
				get {
					render "GET stuff"
				}
				post {
					render "POST stuff"
				}
                put {
                    render "PUST stuff"
                }
			}
		}
	}
}
