package mohsin.reza.movieapp.ui

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mohsin.reza.movieapp.network.model.ShelveItem

class ShelveViewModel(val shelveItem: ShelveItem) : ViewModel() {
    var selectedScrollState = MutableLiveData<Parcelable>()
}
