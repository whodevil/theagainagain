package theagainagain

import spock.lang.Specification
import theagainagain.configuration.EnvironmentHelper
import theagainagain.configuration.EnvironmentProvider
import theagainagain.configuration.ServiceConfiguration

class ServiceConfigurationTest extends Specification {

    EnvironmentHelper environmentHelper
    EnvironmentProvider environmentProvider
    def setup() {
        environmentProvider = Mock(EnvironmentProvider)
        environmentHelper = new EnvironmentHelper(environmentProvider)
    }

    def "test getPort returns default"() {
        given:
        environmentProvider.get("PORT") >> ""
        def configuration = new ServiceConfiguration(environmentHelper)

        when:
        def port = configuration.getPort()

        then:
        port == 5000
    }

    def "test getPort returns non-default"() {
        given:
        environmentProvider.get("PORT") >> "8080"
        def configuration = new ServiceConfiguration(environmentHelper)

        when:
        def port = configuration.getPort()

        then:
        port == 8080
    }
}
