package theagainagain.graphql

import graphql.schema.Coercing
import graphql.schema.GraphQLScalarType
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.TypeRuntimeWiring
import org.apache.commons.fileupload.FileItem
import theagainagain.SparkApplicationException

fun buildRuntimeWiring(dataFetcherIndex: DataFetcherIndex): RuntimeWiring {
    return RuntimeWiring.newRuntimeWiring()
            .type("Query") { builder: TypeRuntimeWiring.Builder ->
                builder.dataFetcher("serviceDefinition", dataFetcherIndex.serviceDefinition())
            }.type("Mutation") {builder: TypeRuntimeWiring.Builder ->
                builder.dataFetcher("imageUpload", dataFetcherIndex.imageUpload())
            }.scalar(uploadScalarType())
            .build()
}

fun uploadScalarType(): GraphQLScalarType =
        GraphQLScalarType
                .newScalar()
                .name("Upload")
                .description("used in uploading files")
                .coercing(object: Coercing<List<FileItem>, Void> {
                    override fun parseValue(input: Any?): List<FileItem>? {
                        return when (input) {
                            is List<*> -> {
                                input.filterIsInstance<FileItem>()
                            }
                            null -> {
                                null
                            }
                            else -> {
                                throw SparkApplicationException(400, "Problem parsing input type for Upload")
                            }
                        }
                    }

                    override fun parseLiteral(input: Any?): List<FileItem> {
                        throw SparkApplicationException(400, "The action was not specified correctly")
                    }

                    override fun serialize(dataFetcherResult: Any?): Void {
                        throw SparkApplicationException(400, "Upload cannot be serialized")
                    }
                })
                .build()