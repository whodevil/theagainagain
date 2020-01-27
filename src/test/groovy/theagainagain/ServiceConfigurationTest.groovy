package theagainagain

import org.slf4j.Logger
import spock.lang.Specification
import theagainagain.configuration.EnvironmentHelper
import theagainagain.configuration.EnvironmentProvider
import theagainagain.configuration.ServiceConfiguration

import static theagainagain.configuration.ServiceConfiguration.*

class ServiceConfigurationTest extends Specification {

    EnvironmentHelper environmentHelper
    EnvironmentProvider environmentProvider
    Logger mockLogger

    def setup() {
        environmentProvider = Mock(EnvironmentProvider)
        environmentHelper = new EnvironmentHelper(environmentProvider)
        mockLogger = Mock(Logger)
    }

    def "test getPort returns default"() {
        given:
        environmentProvider.get(PORT) >> ""
        def configuration = new ServiceConfiguration(environmentHelper, mockLogger, "123")

        when:
        def port = configuration.getPort()

        then:
        port == PORT_DEFAULT
    }

    def "test getPort returns non-default"() {
        given:
        environmentProvider.get(PORT) >> "${PORT_DEFAULT + 1}"
        def configuration = new ServiceConfiguration(environmentHelper, mockLogger, "123")

        when:
        def port = configuration.getPort()

        then:
        port == PORT_DEFAULT + 1
    }

    def "test redirectToSsl returns default value"() {
        given:
        environmentProvider.get(ENABLE_SSL_REDIRECT) >> ""
        def configuration = new ServiceConfiguration(environmentHelper, mockLogger, "123")

        when:
        def enabled = configuration.sslRedirectEnabled()

        then:
        enabled == ENABLE_SSL_REDIRECT_DEFAULT
    }

    def "test redirectToSsl can be enabled"() {
        given:
        environmentProvider.get(ENABLE_SSL_REDIRECT) >> "true"
        def configuration = new ServiceConfiguration(environmentHelper, mockLogger, "123")

        when:
        def enabled = configuration.sslRedirectEnabled()

        then:
        enabled
    }

    def "test ServiceConfiguration log environment logs expected keys"() {
        given:
        environmentProvider.keys >> ["some", "keys"]
        def configuration = new ServiceConfiguration(environmentHelper, mockLogger, "123")

        when:
        configuration.logConfig()

        then:
        1 * mockLogger.info("ENVIRONMENT KEYS: [some,keys]")
    }

    def "test version returned"() {
        given:
        def version = "321"
        def configuration = new ServiceConfiguration(environmentHelper, mockLogger, version)

        when:
        def observedVersion = configuration.getVersion()

        then:
        version == observedVersion
    }
}
