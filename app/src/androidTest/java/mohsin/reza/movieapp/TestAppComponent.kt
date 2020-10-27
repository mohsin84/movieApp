package mohsin.reza.movieapp

import dagger.Component
import mohsin.reza.movieapp.di.AppComponent
import mohsin.reza.movieapp.di.AppModule
import mohsin.reza.movieapp.di.NavigationModule
import mohsin.reza.movieapp.di.NetworkModule
import mohsin.reza.movieapp.home.HomeFragmentTest
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        NavigationModule::class
    ]
)
interface TestAppComponent : AppComponent {
    fun inject(fragment: HomeFragmentTest)
}