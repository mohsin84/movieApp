package mohsin.reza.movieapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import mohsin.reza.movieapp.App
import javax.inject.Singleton

/**
 * App Module
 */
@Module
open class AppModule(val app: App) {

    @Provides
    @Singleton
    fun app(): App = app

    @Provides
    @Singleton
    fun context(): Context = app
}
