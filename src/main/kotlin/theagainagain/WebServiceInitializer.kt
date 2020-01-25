package theagainagain

import com.google.common.collect.Lists
import com.google.common.net.HttpHeaders
import com.google.inject.Inject
import spark.Filter
import spark.Request
import spark.Response
import spark.Spark

class WebServiceInitializer @Inject constructor(private val configuration: ServiceConfiguration) {
    fun initialize(setupEndpoints: Runnable) {
        Spark.port(configuration.getPort())
        setupEndpoints.run()

        setupCorsForOptionsHttpMethod()
        Spark.before(Filter { request: Request?, response: Response? ->
            setCorsHeaderOnResponse(response)
            applyCsrfProtection(request)
        })
        Spark.awaitInitialization()
        Spark.exception(
                Exception::class.java,
                SparkExceptionHandler())
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
