package theagainagain.configuration

import theagainagain.OpenForTesting

@OpenForTesting
class EnvironmentProvider {
    fun get(key: String): String {
        return  System.getenv(key) ?: ""
    }

    fun getKeys(): List<String> {
        return System.getenv().keys.toMutableList()
    }

    fun getProperty(propertyName: String): String {
        return System.getProperties().getProperty(propertyName)
    }
}
