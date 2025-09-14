import geb.Page
import groovy.util.logging.Slf4j

@Slf4j
class SmokePage extends Page {
    static url = 'https://ifconfig.co'
    static at = {
        title == 'What is my IP address? — ifconfig.co'
    }
    static content = {
        titleInContent { $('h1') }
    }
}
