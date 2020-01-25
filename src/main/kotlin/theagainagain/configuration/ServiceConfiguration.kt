package theagainagain.configuration

import com.google.inject.Inject

class ServiceConfiguration @Inject constructor(val env: EnvironmentHelper) {
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
}
