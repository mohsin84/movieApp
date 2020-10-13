package mohsin.reza.movieapp.utils

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectionUtil @Inject constructor(private val context: Context) {

    fun hasInternet() = context.isInternetConnected
}