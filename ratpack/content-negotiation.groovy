@Grab('io.ratpack:ratpack-groovy:1.8.0')
@GrabExclude('org.codehaus.groovy:groovy-all')
@Grab('org.slf4j:slf4j-simple:1.7.30')

import static ratpack.groovy.Groovy.ratpack
import static groovy.json.JsonOutput.toJson

class User {
	String username
	String email
}

def user1 = new User(username: 'Ala', email: 'ma@kota.tv')

def user2 = new User().tap{
	username = 'Ola'
	email = 'ma@psa.tv'
}

def users = [user1, user2]

ratpack {
	handlers {
		get("users") {
			byContent {	
				html {}
				json {}
				xml {}
			}
		}
	}
}
