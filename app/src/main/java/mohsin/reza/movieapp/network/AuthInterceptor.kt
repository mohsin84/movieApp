package mohsin.reza.movieapp.network

import okhttp3.Interceptor
import okhttp3.Response

open class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}