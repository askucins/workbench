package parameterize

import groovy.util.logging.Slf4j

@Slf4j
@Singleton
class ConfigurationProvider {
    static env = [
        dev : [id: '01-DEV', label: 'This is dev'] as Configuration,
        test: [id: '02-TEST', label: 'This is test'] as Configuration,
        prod: [id: '03-PROD', label: 'This is prod'] as Configuration
    ]

    private static returnIterator() {
        ConfigurationProvider.env.values().sort { it.id }.iterator()
    }

    private static envs = returnIterator()

    static Configuration next() {
        Configuration current = envs.next()
        log.debug "Current config: {}", current
        current
    }

    static void reset() {
        envs = returnIterator()
    }
}
