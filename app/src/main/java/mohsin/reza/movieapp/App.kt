package mohsin.reza.movieapp

import android.app.Application
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import mohsin.reza.movieapp.di.AppComponent
import mohsin.reza.movieapp.di.AppModule
import mohsin.reza.movieapp.di.DaggerAppComponent
import mohsin.reza.movieapp.di.NetworkModule
import timber.log.Timber
import java.io.IOException
import java.net.SocketException

/**
 * Application object for library initialisation
 */
class App : Application() {

    companion object {
        lateinit var app: App
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        @Suppress("UNCHECKED_CAST")
        app = this
        appComponent = initialiseAppComponent()

        Timber.plant(getTimberTree())

        RxJavaPlugins.setErrorHandler { exception ->
            val e: Throwable? = if (exception is UndeliverableException) {
                exception.cause
            } else {
                exception
            }

            if (e is IOException || e is SocketException) {
                // Network problem or API that throws on cancellation
                return@setErrorHandler
            }
            if (e is InterruptedException) {
                // Some blocking code was interrupted by a dispose call
                return@setErrorHandler
            }
            if (e is NullPointerException || e is IllegalArgumentException) {
                // Bug in the application
                Thread.currentThread()
                    .uncaughtExceptionHandler?.uncaughtException(Thread.currentThread(), e)
                return@setErrorHandler
            }
            if (e is IllegalStateException) {
                // Bug in RxJava or in a custom operator
                Thread.currentThread()
                    .uncaughtExceptionHandler?.uncaughtException(Thread.currentThread(), e)
                return@setErrorHandler
            }
            Timber.e(e, "Undeliverable exception received")
        }
    }

    fun getTimberTree(): Timber.Tree {
        return Timber.DebugTree()
    }

    fun initialiseAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule(this))
            .build()
    }
}
