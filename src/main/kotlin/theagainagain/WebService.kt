package theagainagain

import com.google.common.net.MediaType
import com.google.common.util.concurrent.AbstractIdleService
import com.google.inject.Inject
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import mu.KotlinLogging
import spark.Request
import spark.Route
import spark.Spark
import theagainagain.configuration.ServiceConfiguration

private val logger = KotlinLogging.logger {}

class WebService @Inject constructor(
        private val webServiceInitializer: WebServiceInitializer,
        private val configuration: ServiceConfiguration) : AbstractIdleService() {
    override fun startUp() {
        logger.info("web service starting")
        webServiceInitializer.initialize(setUpEndpoints)
    }

    override fun shutDown() {
        logger.info("shutting down")
        Spark.stop()
    }

    val setUpEndpoints = Runnable {
        logger.info("setting up endpoints")
        Spark.get("/webhook/github", MediaType.JSON_UTF_8.toString(), githubWebHook)
    }

    private val githubWebHook = Route { request: Request, _ ->
        logger.info("handling webhook github.")
        "OK"
    }
}
