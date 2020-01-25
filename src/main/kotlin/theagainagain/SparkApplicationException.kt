package theagainagain


class SparkApplicationException(val statusCode: Int, val errorMessage: String) : RuntimeException(errorMessage) {

}
