package misc.process

// Based on https://www.developer.com/java/data/understanding-java-process-and-java-processbuilder.html

class ProcessDemoMemory {

    static void printMemoryReport(Runtime r) {
        Integer paddingSize = 30
        println "Total memory: ${r.totalMemory()}".padLeft(paddingSize, ' ')
        println "Free memory: ${r.freeMemory()}".padLeft(paddingSize, ' ')
        println "Memory occupied: ${r.totalMemory() - r.freeMemory()}".padLeft(paddingSize, ' ')
        println ""
    }

    static void main(String... args) throws Exception {

        Runtime r = Runtime.getRuntime()
        println "No of Processor: ${r.availableProcessors()}"
        println ""

        println "::Memory status::before objects generation"
        printMemoryReport(r)

        (1..10000).each { new Object() }

        println "::Memory status::after objects generation"
        printMemoryReport(r)

        r.gc()

        println "::Memory status::after GC run"
        printMemoryReport(r)
    }
}
