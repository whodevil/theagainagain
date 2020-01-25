package theagainagain.configuration

import theagainagain.OpenForTesting

@OpenForTesting
class EnvironmentProvider {
    fun get(key: String): String {
        return  System.getenv(key) ?: ""
    }
}
