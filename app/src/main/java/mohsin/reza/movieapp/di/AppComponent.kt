package mohsin.reza.movieapp.di

import dagger.Component
import mohsin.reza.movieapp.MainActivity

import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {
    fun inject(activity: MainActivity)
}
