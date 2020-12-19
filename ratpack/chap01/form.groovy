import ratpack.form.Form

import static ratpack.groovy.Groovy.ratpack
@Grab('io.ratpack:ratpack-groovy:1.8.0')
@GrabExclude('org.codehaus.groovy:groovy-all')
@Grab('org.slf4j:slf4j-simple:1.7.30')

import static ratpack.groovy.Groovy.ratpack

static String formHtml() {
    """\
	<!DOCTYPE html>             
	<html>
		<body>
			<form method="POST">
				<div>
					<label for="checked">Check</label>
					<input type="checkbox" id="checked" name="checked">
				</div>
				<div>
					<label for="name">Name</label>
					<input type="text" id="name" name="name">
				</div>
				<div>
					<input type="submit">
				</div>
			</form>
		</body>
	</html>""".stripIndent()
}

static String formResponse(String name, String msg) {
    """\
	<!DOCTYPE html>
	<html>
		<body>
			<h1>Welcome, ${name}!</h1>
			<span>${msg}</span>
		</body>
	</html>
	""".stripIndent()
}

ratpack {
    handlers {
        all {
            byMethod {
                get {
                    response.send "text/html", formHtml()
                }
                post {
                    parse(Form).then { formData ->
                        def msg = formData.checked
                            ? "Thanks for the check!"
                            : "Why didn't you check?"
                        response.send "text/html", formResponse(formData.name ?: 'Guest', msg)
                    }
                }
            }
        }
    }
}
