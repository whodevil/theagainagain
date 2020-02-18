package theagainagain.graphql

import com.beust.klaxon.Klaxon
import com.google.gson.Gson
import com.google.inject.Inject
import graphql.ExecutionInput
import graphql.ExecutionResult
import graphql.GraphQL
import graphql.schema.GraphQLSchema
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.TypeDefinitionRegistry
import org.apache.commons.fileupload.FileItem

class GraphQlHandler @Inject constructor(
        typeDefinitionRegistry: TypeDefinitionRegistry,
        schemaGenerator: SchemaGenerator,
        private val dataFetcherIndex: DataFetcherIndex
) {
    private val runtimeWiring: RuntimeWiring = buildRuntimeWiring(dataFetcherIndex)
    private var graphQLSchema: GraphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring)
    private val graphQL: GraphQL = GraphQL.newGraphQL(graphQLSchema).build()

    fun handle(body: String?): String {
        if(body == null) {
            return "{\"error\":\"message\"}"
        }
        val executionResult = graphQL.execute(body)
        return handleExecutionResult(executionResult)
    }

    fun handle(operations: List<String>, items: List<FileItem>): String {
        val query = Klaxon().parse<MutationData>(operations.first())
        query!!.variables["file"] = items
        val executionInput = ExecutionInput
                .newExecutionInput()
                .operationName(query.operationName)
                .variables(query.variables)
                .query(query.query)
                .build()
        val executionResult = graphQL.execute(executionInput)
        return  handleExecutionResult(executionResult)
    }

    private fun handleExecutionResult(executionResult: ExecutionResult): String {
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

data class MutationData(val operationName: String, val variables: MutableMap<String, Any>, val query: String)
