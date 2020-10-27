package mohsin.reza.movieapp

import mohsin.reza.movieapp.network.NetworkSettings

class TestNetworkSettingsImpl : NetworkSettings {
//    override var isInMockMode = true
    override var isConnectedToInternet: Boolean = true
}
