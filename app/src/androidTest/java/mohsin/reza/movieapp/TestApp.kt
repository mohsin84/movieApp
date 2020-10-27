package mohsin.reza.movieapp

import mohsin.reza.movieapp.di.AppModule

class TestApp : App() {
    companion object {
        val app: App
            get() = App.app
    }

    override fun initialiseAppComponent(): TestAppComponent {
        return DaggerTestAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(TestNetworkModule(this))
            .build()
    }
}