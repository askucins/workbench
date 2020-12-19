import static groovy.json.JsonOutput.toJson
import static ratpack.groovy.Groovy.ratpack
@Grab('io.ratpack:ratpack-groovy:1.8.0')
@GrabExclude('org.codehaus.groovy:groovy-all')
@Grab('org.slf4j:slf4j-simple:1.7.30')

import static ratpack.groovy.Groovy.ratpack

class User {
    String username
    String email
}

def user1 = new User(username: 'Ala', email: 'ma@kota.tv')

def user2 = new User().tap {
    username = 'Ola'
    email = 'ma@psa.tv'
}

def users = [user1, user2]

ratpack {
    handlers {
        get("users") {
            byContent {
                html {
                    def usersHtml = users.collect { user ->
                        """\
						|<div>
						|<b>Username:</b>${user.username}
						|<b>Email:</b> ${user.email}
						|</div>""".stripMargin()
                    }.join()

                    render """\
						|<!DOCTYPE html>
						|<html>
						|<head>
						|<title>User List</title>
						|</head>
						|<body>
						|<h1>Users</h1>
						|${usersHtml}
						|</body>
						|</html>""".stripMargin()
                }
                json {
                    render toJson(users)
                }
                xml {
                    def xmlStrings = users.collect { user ->
                        """
						<user>
						<username>${user.username}</username>
						<email>${user.email}</email>
						</user>
						""".toString().stripIndent()
                    }.join()

                    render "<users>${xmlStrings}</users>"
                }
                type("application/vnd.app.custom+json") {
                    render toJson([
                        some_custom_data: "my custom data",
                        type            : "custom-users",
                        users           : users
                    ])
                }
                //noMatch {
                //	response.status 400
                //	render "negotation not possible"
                //}
                noMatch "application/json"
            }
        }
    }
}
