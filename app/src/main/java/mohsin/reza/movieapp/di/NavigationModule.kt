package mohsin.reza.movieapp.di

import dagger.Module
import dagger.Provides
import mohsin.reza.movieapp.utils.Navigator
import javax.inject.Singleton

@Module
class NavigationModule {
    @Provides
    @Singleton
    fun providesNavigator(): Navigator = Navigator()
}