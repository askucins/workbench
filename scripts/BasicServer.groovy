// From http://voituk.kiev.ua/2007/08/26/simple-http-server-in-10-lines-of-groovy-code-en/
/*
    Usage:
    groovy -l 8000 BasicServer.groovy

*/
if (init)
    data = "";

if (line.size() > 0) {
    data += line + "\n"
} else {
    println "HTTP/1.0 200 OK\n"
    println data
    return "success"
}
