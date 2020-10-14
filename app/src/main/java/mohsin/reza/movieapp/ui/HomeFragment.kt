package mohsin.reza.movieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_home.home_page_recycler_view
import mohsin.reza.movieapp.App
import mohsin.reza.movieapp.R
import mohsin.reza.movieapp.network.model.ResourceState
import mohsin.reza.movieapp.utils.ViewModelFactory
import mohsin.reza.movieapp.utils.safeSize
import timber.log.Timber
import javax.inject.Inject

class HomeFragment : Fragment() {
    @Inject
    lateinit var homePageVMFactory: ViewModelFactory<HomePageVM>

    private val viewModel by lazy {
        ViewModelProviders.of(this, homePageVMFactory).get(HomePageVM::class.java)
    }
    private val homePageRecyclerView
        get() = home_page_recycler_view

    private val adapter: HomePageRecyclerViewAdapter
        get() = homePageRecyclerView.adapter as HomePageRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.app.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homePageRecyclerView.adapter = HomePageRecyclerViewAdapter({})
        viewModel.requestMovieList()
        viewModel.movieListLiveData.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                ResourceState.LOADING -> {
                    // no-op
                }
                ResourceState.SUCCESS -> {
                    adapter.items = resource.data ?: emptyList()
                }
                ResourceState.ERROR -> {
                    // no-op
                }
            }
            Timber.d("Data found ${resource.status} ${resource.data.safeSize}")
        })
    }
}