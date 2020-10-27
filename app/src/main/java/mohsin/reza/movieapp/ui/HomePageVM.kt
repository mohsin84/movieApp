package mohsin.reza.movieapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import mohsin.reza.movieapp.network.MovieRepository
import mohsin.reza.movieapp.network.model.Resource
import mohsin.reza.movieapp.utils.scheduler.Schedulers
import javax.inject.Inject

class HomePageVM @Inject constructor(
    private val movieRepository: MovieRepository,
    private val schedulers: Schedulers
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val movieListMutableLiveData = MutableLiveData<Resource<List<ShelveViewModel>>>()

    val movieListLiveData: LiveData<Resource<List<ShelveViewModel>>>
        get() = movieListMutableLiveData

    fun requestMovieList() {
        movieRepository.getPopularMoviesList()
            .map {
                val list = mutableListOf<ShelveViewModel>()
                it.map { shelveItem ->
                    val item = ShelveViewModel(shelveItem)
                    list.add(item)
                }
                list
            }
            .observeOn(schedulers.main())
            .doOnSubscribe {
                movieListMutableLiveData.postValue(Resource.loading())
            }
            .doOnError { e ->
                movieListMutableLiveData.postValue(Resource.error(e))
            }
            .subscribe {
                movieListMutableLiveData.postValue(Resource.success(it))
            }.addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}