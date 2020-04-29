package misc.process

import java.util.concurrent.TimeUnit

// Based on https://www.developer.com/java/data/understanding-java-process-and-java-processbuilder.html

class ProcessDemoExec {

    static void main(String... args) throws Exception {
        Runtime r = Runtime.getRuntime()
        Process p = r.exec("firefox")
        p.waitFor(10, TimeUnit.SECONDS)
        p.destroy()
    }
}
