package chap02complex

enum ClientVersion {
    V1("Microservice Client v1.0"),
    V2("Microservice Client v2.0"),
    V3("Microservice Client v3.0")

    String version

    ClientVersion(String version) {
        this.version = version
    }

    static ClientVersion fromString(String val) {
        values().find {it.version == val } ?: null
    }
}

