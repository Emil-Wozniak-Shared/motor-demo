package pl.ejdev.exception

class FunctionProblem(message: String?) : RuntimeException(message) {
    constructor(
        title: String?,
        cause: String?
    ) : this(creteMsg(title, cause))

    private companion object {
        const val PATTERN = "[errorcode="
        fun creteMsg(title: String?, cause: String?): String {
            val actualTitle = title?.split(PATTERN)?.get(1)?.split("]")?.get(1)
            val actualCase = cause?.split(PATTERN)?.get(1)?.split("]")?.get(1)
            return "Problem with function\n\n$actualTitle\n\n$actualCase"
        }
    }
}