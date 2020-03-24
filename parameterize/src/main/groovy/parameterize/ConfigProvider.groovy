package parameterize

import groovy.util.logging.Slf4j

@Slf4j
@Singleton
class ConfigProvider {
    static env = [
        dev : [id: '01-DEV', label: 'This is dev'] as Config,
        test: [id: '02-TEST', label: 'This is test'] as Config,
        prod: [id: '03-PROD', label: 'This is prod'] as Config
    ]

    private static  returnIterator() {
        ConfigProvider.env.values().sort { it.id }.iterator()
    }

    private static envs = returnIterator()

    static Config next() {
        Config current = envs.next()
        log.debug "Current config: {}", current
        current
    }

    static void reset() {
        envs = returnIterator()
    }
}
