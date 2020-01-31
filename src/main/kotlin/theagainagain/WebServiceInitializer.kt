package theagainagain

import com.google.common.collect.Lists
import com.google.common.net.HttpHeaders
import com.google.inject.Inject
import mu.KotlinLogging
import spark.Filter
import spark.Request
import spark.Response
import spark.Spark
import theagainagain.configuration.ServiceConfiguration

private val logger = KotlinLogging.logger {}

class WebServiceInitializer @Inject constructor(private val configuration: ServiceConfiguration) {
    fun initialize(setupEndpoints: Runnable) {
        Spark.port(configuration.getPort())

        // Static files must come before the endpoints
        Spark.staticFiles.externalLocation("${System.getProperties().getProperty("user.dir")}/ui/build");
        setupEndpoints.run()

        hardening()
        Spark.awaitInitialization()
        Spark.exception(
                Exception::class.java,
                SparkExceptionHandler())
    }

    private fun hardening() {
        setupCorsForOptionsHttpMethod()
        Spark.before(Filter { request: Request?, response: Response? ->
            sslRedirect(request, response)
            setCorsHeaderOnResponse(response)
            applyCsrfProtection(request)
        })
    }

    private fun sslRedirect(request: Request?, response: Response?) {
        val url: String = request?.url() ?: ""
        if (configuration.sslRedirectEnabled() && url.startsWith("http://")) {
            logger.info("redirecting $url to https")
            val split = url.split("http://")
            response?.redirect("https://${split[1]}")
        }
    }

    private fun setCorsHeaderOnResponse(response: Response?) {
        response!!.header("Access-Control-Allow-Origin", "*")
    }

    private fun applyCsrfProtection(request: Request?) {
        val customHeader = request!!.headers(HttpHeaders.X_REQUESTED_WITH)
        val method = request.requestMethod()
        val protectedMethods: List<String> = Lists.newArrayList("POST", "DELETE", "PUT")
        if (customHeader == null && protectedMethods.contains(method)) {
            val errorMessage = "{\"message\": \"The X-Requested-With header is required\"}"
            Spark.halt(401, errorMessage)
        }
    }

    private fun setupCorsForOptionsHttpMethod() {
        Spark.options("/*") { request: Request, response: Response ->
            val accessControlRequestHeaders = request.headers("Access-Control-Request-Headers")
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders)
            }
            val accessControlRequestMethod = request.headers("Access-Control-Request-Method")
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod)
            }
            "OK"
        }
    }
}
