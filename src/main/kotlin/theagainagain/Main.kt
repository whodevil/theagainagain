package theagainagain

import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.Service
import com.google.common.util.concurrent.ServiceManager
import com.google.inject.Guice
import com.google.inject.Inject
import dev.misfitlabs.kotlinguice4.getInstance
import mu.KotlinLogging
import org.slf4j.bridge.SLF4JBridgeHandler
import theagainagain.configuration.ServiceConfiguration

private val logger = KotlinLogging.logger {}

class Main @Inject constructor(val webService: WebService, val configuration: ServiceConfiguration)
fun main(args: Array<String>) {
    initLogging()
    val injector = Guice.createInjector(ServiceModule())
    val main = injector.getInstance<Main>()
    main.apply {
        logger.info("Starting Services")
        val serviceManager = ServiceManager(ImmutableList.of<Service>(this.webService))
        serviceManager.startAsync()
        serviceManager.awaitStopped()
    }
}

fun initLogging() {
    SLF4JBridgeHandler.removeHandlersForRootLogger()
    SLF4JBridgeHandler.install()
}
