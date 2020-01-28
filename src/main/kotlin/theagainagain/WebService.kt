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
                    meta {
                        attributes["property"]="og:title"
                        attributes["content"]="Off The Cob - The art of the Again Again"
                    }
                    meta {
                        attributes["property"]="og:type"
                        attributes["content"]="website"
                    }
                    meta {
                        attributes["property"]="og:image"
                        attributes["content"]="https://www.offthecob.info/R001072.jpg"
                    }
                    style {
                        unsafe {
                            raw("""
                                body {
                                    background-color: #ff0000;
                                    color: #ccc;
                                }
                                .footer {
                                  position: fixed;
                                  left: 0;
                                  bottom: 0;
                                  width: 100%;
                                  background-color: red;
                                  color: white;
                                  text-align: center;
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
                    div(classes = "footer") {
                        p {
                            +configuration.getVersion()
                        }
                    }
                }
            }
            appendln()
        }
    }
}
