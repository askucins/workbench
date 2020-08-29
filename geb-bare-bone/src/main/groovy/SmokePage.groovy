import geb.Page
import groovy.util.logging.Slf4j

@Slf4j
class SmokePage extends Page {
    static url = 'https://httpstat.us'
    static at = {
        title == 'httpstat.us'
    }
    static content = {
        titleInContent { $('div#content header h1#title') }
        /*
        <div id='content'>
            <header>
                <h1 id='title'>httpstat.us</h1>
        */
    }
}
