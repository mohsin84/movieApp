package mohsin.reza.movieapp.di

import dagger.Component
import mohsin.reza.movieapp.MainActivity
import mohsin.reza.movieapp.ui.HomeFragment
import mohsin.reza.movieapp.ui.MovieDetailsFragment

import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        NavigationModule::class
    ]
)
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(homeFragment: HomeFragment)
    fun inject(movieDetailsFragment: MovieDetailsFragment)
}
