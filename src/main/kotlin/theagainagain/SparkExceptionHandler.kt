package theagainagain

import com.google.common.net.MediaType
import mu.KotlinLogging
import spark.ExceptionHandler
import spark.Request
import spark.Response
import java.net.HttpURLConnection

private val logger = KotlinLogging.logger {}
class SparkExceptionHandler : ExceptionHandler<Exception> {
    override fun handle(exception: Exception?, request: Request?, response: Response?) {
        if (exception is SparkApplicationException) {
            logger.error("An application error occurred", exception)
            response!!.status(exception.statusCode)
            response.type(MediaType.PLAIN_TEXT_UTF_8.toString())
            response.body(exception.message)
        } else {
            logger.error("An internal error occurred", exception)
            response!!.status(HttpURLConnection.HTTP_INTERNAL_ERROR)
            response.type(MediaType.PLAIN_TEXT_UTF_8.toString())
            response.body("unable to handle response")
        }
    }

}
