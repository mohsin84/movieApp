package mohsin.reza.movieapp

import android.app.Application
import mohsin.reza.movieapp.di.NetworkModule
import mohsin.reza.movieapp.network.AuthInterceptor
import mohsin.reza.movieapp.network.NetworkSettings

class TestNetworkModule(app: Application) : NetworkModule(app) {
    override fun provideNetworkSettings(): NetworkSettings {
        return TestNetworkSettingsImpl()
    }

    override fun provideAuthInterceptor(networkSettings: NetworkSettings): AuthInterceptor {
        return MockInterceptor(networkSettings)
    }
}
