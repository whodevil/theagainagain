package theagainagain.configuration

import com.google.inject.Inject
import com.google.inject.name.Named
import org.slf4j.Logger
import theagainagain.OpenForTesting
import theagainagain.ServiceModule.Companion.SERVICE_CONFIG_LOGGER
import theagainagain.ServiceModule.Companion.SERVICE_VERSION

@OpenForTesting
class ServiceConfiguration
@Inject constructor(private val env: EnvironmentHelper,
                    @Named(SERVICE_CONFIG_LOGGER) private val logger: Logger,
                    @Named(SERVICE_VERSION) private val serviceVersion: String) {
    companion object {
        const val PORT: String = "PORT"
        const val PORT_DEFAULT: Int = 8080
        const val ENABLE_SSL_REDIRECT: String = "ENABLE_SSL_REDIRECT"
        const val ENABLE_SSL_REDIRECT_DEFAULT: Boolean = false
        const val AWS_ACCESS_KEY_ID: String = "AWS_ACCESS_KEY_ID"
        const val UI_DOWNLOADED_LOCATION: String = "/build/ui/build"
        const val UI_LOCALLY_BUILT_LOCATION: String = "/ui/build"
        const val USER_DIR: String = "user.dir"
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

    fun shouldFetchUi(): Boolean {
        return env.getKeys().contains(AWS_ACCESS_KEY_ID)
    }

    fun getUiLocation(): String {
        return if(shouldFetchUi()){
            "${env.getProperty(USER_DIR)}$UI_DOWNLOADED_LOCATION"
        } else {
            "${env.getProperty(USER_DIR)}$UI_LOCALLY_BUILT_LOCATION"
        }
    }
}
