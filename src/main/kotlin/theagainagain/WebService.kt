package theagainagain

import com.beust.klaxon.Klaxon
import com.google.common.net.MediaType
import com.google.common.util.concurrent.AbstractIdleService
import com.google.inject.Inject
import mu.KotlinLogging
import spark.Request
import spark.Response
import spark.Route
import spark.Spark
import theagainagain.configuration.ServiceConfiguration
import theagainagain.graphql.GraphQlHandler

private val logger = KotlinLogging.logger {}

class WebService @Inject constructor(
        private val webServiceInitializer: WebServiceInitializer,
        private val configuration: ServiceConfiguration,
        private val graphQL: GraphQlHandler) : AbstractIdleService() {
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
        Spark.post("/graphql", MediaType.JSON_UTF_8.toString(), graphql)
    }

    private val githubWebHook = Route { request: Request, _ ->
        logger.info("handling webhook github.")
        "OK"
    }

    private val graphql = Route { request: Request, response: Response ->
        logger.info("handling graphql post.")
        response.type(MediaType.JSON_UTF_8.toString())
        val query = Klaxon().parse<RequestQuery>(request.body())
        val result = graphQL.handle(query?.query)
        result
    }
}

data class RequestQuery constructor(val query: String)