package theagainagain

import com.google.inject.Provider
import com.google.inject.name.Names
import dev.misfitlabs.kotlinguice4.KotlinModule
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import theagainagain.configuration.ServiceConfiguration

class ServiceModule : KotlinModule() {
    companion object {
        const val SERVICE_CONFIG_LOGGER: String = "ServiceConfigurationLogger"
    }

    override fun configure() {
        super.configure()
        bind<Logger>().annotatedWith(Names.named(SERVICE_CONFIG_LOGGER)).toProvider<ServiceConfigurationLoggerProvider>()
    }
}

class ServiceConfigurationLoggerProvider: Provider<Logger> {
    override fun get(): Logger {
        return LoggerFactory.getLogger(ServiceConfiguration.javaClass)
    }
}
