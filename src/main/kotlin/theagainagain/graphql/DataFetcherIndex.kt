package theagainagain.graphql

import com.google.inject.Inject
import graphql.schema.DataFetcher
import theagainagain.configuration.ServiceConfiguration


class DataFetcherIndex @Inject constructor(private val configuration: ServiceConfiguration) {
    fun serviceDefinition(): DataFetcher<ServiceDefinition> {
        return DataFetcher {
            ServiceDefinition(configuration.getVersion())
        }
    }
}

data class ServiceDefinition constructor(var version: String)
