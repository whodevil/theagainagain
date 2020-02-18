package theagainagain

import com.beust.klaxon.Klaxon
import com.google.common.net.MediaType
import com.google.common.util.concurrent.AbstractIdleService
import com.google.inject.Inject
import mu.KotlinLogging
import org.apache.commons.fileupload.FileItem
import org.apache.commons.fileupload.servlet.ServletFileUpload
import spark.Request
import spark.Response
import spark.Route
import spark.Spark
import theagainagain.graphql.GraphQlHandler


private val logger = KotlinLogging.logger {}

class WebService @Inject constructor(
        private val webServiceInitializer: WebServiceInitializer,
        private val graphQL: GraphQlHandler,
        private val fileUpload: ServletFileUpload) : AbstractIdleService() {
    override fun startUp() {
        logger.info("web service starting")
        webServiceInitializer.initialize(setUpEndpoints)
    }

    override fun shutDown() {
        logger.info("shutting down")
        Spark.stop()
    }

    private val setUpEndpoints = Runnable {
        logger.info("setting up endpoints")
        Spark.get("/webhook/github", MediaType.JSON_UTF_8.toString(), githubWebHook)
        Spark.post("/graphql", graphql)
    }

    private val githubWebHook = Route { request: Request, _ ->
        logger.info("handling webhook github.")
        "OK"
    }

    private val graphql = Route { request: Request, response: Response ->
        logger.info("handling graphql post.")
        response.type(MediaType.JSON_UTF_8.toString())
        if (request.contentType().startsWith("multipart/form-data")) {
            fileUpload(request)
        } else {
            query(request)
        }
    }

    private fun fileUpload(request: Request): String {
        val items: List<FileItem> = fileUpload.parseRequest(request.raw())
        val operations: List<String> = items.filter { it.fieldName == "operations" }.map { it.string }
        val images: List<FileItem> = items.filter { it.name != null }
        return graphQL.handle(operations, images)
    }

    private fun query(request: Request): String {
        val query = Klaxon().parse<RequestQuery>(request.body())
        return graphQL.handle(query?.query)
    }
}

data class RequestQuery constructor(val query: String)