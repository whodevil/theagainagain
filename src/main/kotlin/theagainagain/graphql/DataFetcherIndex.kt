package theagainagain.graphql

import com.google.inject.Inject
import graphql.schema.DataFetcher
import theagainagain.configuration.ServiceConfiguration
import javax.servlet.http.Part


class DataFetcherIndex @Inject constructor(private val configuration: ServiceConfiguration) {
    fun serviceDefinition(): DataFetcher<ServiceDefinition> {
        return DataFetcher {
            ServiceDefinition(configuration.getVersion())
        }
    }

    fun imageUpload(): DataFetcher<Image> {
        return DataFetcher {
            val file: List<Part> = it.arguments["file"] as List<Part>
            Image("file", "", "", "", "")
        }
    }
}

data class ServiceDefinition constructor(var version: String)
data class Image(
        val id: String,
        val url: String,
        val filename: String,
        val mimetype: String,
        val encoding: String
) {
    constructor(filename: String, mimetype: String, encoding: String) :
            this("", "", filename, mimetype, encoding)
}