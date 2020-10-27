package mohsin.reza.movieapp

import mohsin.reza.movieapp.network.AuthInterceptor
import mohsin.reza.movieapp.network.NetworkSettings
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class MockInterceptor(private val networkSettings: NetworkSettings) : AuthInterceptor() {
    companion object {
        private const val RESPONSE_200 = 200
        private const val RESPONSE_504 = 504
        private const val MOVIE_PATH = "/3/movie/popular"
        private val mediaTypeJson = "application/json".toMediaTypeOrNull()
        private val errorResponse = { request: Request ->
            Response.Builder()
                .body("error".toResponseBody(mediaTypeJson))
                .request(request)
                .protocol(Protocol.HTTP_2)
                .code(RESPONSE_504)
                .message("")
                .build()
        }

        private val successResponse = { request: Request ->
            val mockJson: String = FileReader.readStringFromFile("popular_movies.json")
            Response.Builder()
                .body(mockJson.toResponseBody(mediaTypeJson))
                .request(request)
                .protocol(Protocol.HTTP_2)
                .code(RESPONSE_200)
                .message("")
                .build()
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return when (request.url.encodedPath) {
            MOVIE_PATH -> {
                if (networkSettings.isConnectedToInternet) {
                    successResponse(request)
                } else {
                    errorResponse(request)
                }
            }
            else -> {
                chain.proceed(request)
            }
        }
    }
}