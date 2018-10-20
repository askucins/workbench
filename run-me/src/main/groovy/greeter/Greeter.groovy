package greeter

class Greeter {
    static void main(String... args) {
        println GreetingFormatter.greeting(args[0])
    }
}