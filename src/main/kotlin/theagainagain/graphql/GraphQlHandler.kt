package theagainagain.graphql

import com.google.gson.Gson
import com.google.inject.Inject
import graphql.GraphQL
import graphql.schema.GraphQLSchema
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.RuntimeWiring.newRuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.TypeDefinitionRegistry
import graphql.schema.idl.TypeRuntimeWiring
import graphql.servlet.apollo.ApolloScalars

class GraphQlHandler @Inject constructor(
        typeDefinitionRegistry: TypeDefinitionRegistry,
        schemaGenerator: SchemaGenerator,
        private val dataFetcherIndex: DataFetcherIndex
) {
    private val runtimeWiring: RuntimeWiring = buildRuntimeWiring()
    private var graphQLSchema: GraphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring)
    val graphQL: GraphQL = GraphQL.newGraphQL(graphQLSchema).build()

    private fun buildRuntimeWiring(): RuntimeWiring {
        return newRuntimeWiring()
                .type("Query") { builder: TypeRuntimeWiring.Builder ->
                    builder.dataFetcher("serviceDefinition", dataFetcherIndex.serviceDefinition())
                }.type("Mutation") {builder: TypeRuntimeWiring.Builder ->
                    builder.dataFetcher("imageUpload", dataFetcherIndex.imageUpload())
                }.scalar(ApolloScalars.Upload)
                .build()
    }

    fun handle(body: String?): String {
        if(body == null) {
            return "{\"error\":\"message\"}"
        }
        val executionResult = graphQL.execute(body)
        return when {
            executionResult.errors.isNotEmpty() -> {
                "{\"error\":\"message\"}"
            }
            executionResult.isDataPresent -> {
                Gson().toJson(executionResult.toSpecification())
            }
            else -> {
                "{}"
            }
        }
    }
}
