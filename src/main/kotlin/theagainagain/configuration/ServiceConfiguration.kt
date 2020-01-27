package theagainagain.configuration

import com.google.inject.Inject
import com.google.inject.name.Named
import org.slf4j.Logger
import theagainagain.ServiceModule.Companion.SERVICE_CONFIG_LOGGER
import theagainagain.ServiceModule.Companion.SERVICE_VERSION

class ServiceConfiguration
@Inject constructor(private val env: EnvironmentHelper,
                    @Named(SERVICE_CONFIG_LOGGER) private val logger: Logger,
                    @Named(SERVICE_VERSION) private val serviceVersion: String) {
    companion object {
        const val PORT: String = "PORT"
        const val PORT_DEFAULT: Int = 5000
        const val ENABLE_SSL_REDIRECT: String = "ENABLE_SSL_REDIRECT"
        const val ENABLE_SSL_REDIRECT_DEFAULT: Boolean = false
    }

    fun getPort(): Int {
        return env.get(PORT, PORT_DEFAULT)
    }

    fun sslRedirectEnabled(): Boolean {
        return env.get(ENABLE_SSL_REDIRECT, ENABLE_SSL_REDIRECT_DEFAULT)
    }

    fun getVersion(): String {
        return serviceVersion
    }

    fun logConfig() {
        logger.info("ENVIRONMENT KEYS: ${env.getKeys().replace("\n", "").replace("\r", "")}")
    }
}
