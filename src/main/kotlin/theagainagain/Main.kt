package theagainagain

import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.Service
import com.google.common.util.concurrent.ServiceManager
import com.google.inject.Guice
import com.google.inject.Inject
import dev.misfitlabs.kotlinguice4.getInstance
import mu.KotlinLogging
import org.slf4j.bridge.SLF4JBridgeHandler
import sun.misc.Signal
import theagainagain.configuration.ServiceConfiguration
import kotlin.system.exitProcess

private val logger = KotlinLogging.logger {}

class Main @Inject constructor(val webService: WebService, val configuration: ServiceConfiguration)
fun main(args: Array<String>) {
    initLogging()
    val injector = Guice.createInjector(ServiceModule())
    val main = injector.getInstance<Main>()
    main.apply {
        configuration.logConfig()
        logger.info("Starting Services")
        val serviceManager = ServiceManager(ImmutableList.of<Service>(this.webService))
        signalHandler(serviceManager)
        serviceManager.startAsync()
        serviceManager.awaitStopped()
        exitProcess(0)
    }
}

fun signalHandler(serviceManager: ServiceManager) {
    Signal.handle(Signal("INT")) {
        logger.info("shutting down")
        serviceManager.stopAsync()
    }
}

fun initLogging() {
    SLF4JBridgeHandler.removeHandlersForRootLogger()
    SLF4JBridgeHandler.install()
}
