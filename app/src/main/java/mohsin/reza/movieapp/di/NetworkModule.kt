package mohsin.reza.movieapp.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import mohsin.reza.movieapp.network.MovieRepository
import mohsin.reza.movieapp.network.MovieServices
import mohsin.reza.movieapp.utils.scheduler.AppSchedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Network Module
 */
@Module
open class NetworkModule(val application: Application, private val versionName: String? = null) {

    companion object {
        private const val PREFERENCES_NAME = "Network"
        private const val BASE_URL =
            "https://api.themoviedb.org/3" // Used for Retrofit initialization only
        private const val TIMEOUT = 3L
    }

    @Singleton
    @Provides
    fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory =
        RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Singleton
    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi).asLenient()

    @Singleton
    @Provides
    fun provideRetrofit(
        httpClient: OkHttpClient,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit = Retrofit.Builder()
        .addCallAdapterFactory(rxJava2CallAdapterFactory)
        .addConverterFactory(moshiConverterFactory)
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        builder.writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS)
        return builder.build()
    }

    @Provides
    @Singleton
    fun providesResources(): Resources = application.resources

    private inline fun <reified T> Retrofit.createApi(): T = create(T::class.java)

    @Singleton
    @Provides
    fun providePreferences() =
        application.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)!!

    @Singleton
    @Provides
    fun provideWeatherService(retrofit: Retrofit): MovieServices = retrofit.createApi()

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieServices: MovieServices
    ) = MovieRepository(movieServices)

    @Singleton
    @Provides
    fun providesAppSchedulers(): mohsin.reza.movieapp.utils.scheduler.Schedulers = AppSchedulers()
}