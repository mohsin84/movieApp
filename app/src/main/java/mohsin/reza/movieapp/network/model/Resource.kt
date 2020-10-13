package mohsin.reza.movieapp.network.model

enum class ResourceState { LOADING, SUCCESS, ERROR }

data class Resource<out T> constructor(
    val status: ResourceState,
    val data: T?,
    val error: Throwable?
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(ResourceState.SUCCESS, data, null)
        }

        fun <T> error(error: Throwable?): Resource<T> {
            return Resource(ResourceState.ERROR, null, error)
        }

        fun <T> loading(): Resource<T> {
            return Resource(ResourceState.LOADING, null, null)
        }
    }
}