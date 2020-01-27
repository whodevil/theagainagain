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

private val logger = KotlinLogging.logger {}

class WebService @Inject constructor(private val webServiceInitializer: WebServiceInitializer) : AbstractIdleService() {
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
        Spark.get("/", MediaType.JSON_UTF_8.toString(), root)
        Spark.get("/webhook/github", MediaType.JSON_UTF_8.toString(), githubWebHook)
    }

    private val githubWebHook = Route { request: Request, _ ->
        logger.info("handling webhook github.")
        "OK"
    }

    private val root = Route { request: Request, _ ->
        logger.info("user agent: {}", request.userAgent())
        buildString {
            appendln("<!DOCTYPE html>")
            appendHTML().html {
                head {
                    style {
                        unsafe {
                            raw("""
                                body {
                                    background-color: #ff0000;
                                    color: #ccc;
                                }
                            """)
                        }
                    }
                }
                body {
                    div {
                        attributes["style"] = "background: black; font-size: 40em; text-align: center"
                        span {
                            +"bro"
                        }
                    }
                }
            }
            appendln()
        }
    }
}
