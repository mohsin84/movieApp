package mohsin.reza.movieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.reactivex.exceptions.CompositeException
import mohsin.reza.movieapp.App
import mohsin.reza.movieapp.R
import mohsin.reza.movieapp.databinding.FragmentHomeBinding
import mohsin.reza.movieapp.databinding.NetworkStatusLayoutBinding
import mohsin.reza.movieapp.network.model.ResourceState
import mohsin.reza.movieapp.utils.Navigator
import mohsin.reza.movieapp.utils.ViewModelFactory
import mohsin.reza.movieapp.utils.isInternetConnected
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class HomeFragment : Fragment() {
    @Inject
    lateinit var homePageVMFactory: ViewModelFactory<HomePageVM>

    private val viewModel by lazy {
        ViewModelProvider(this, homePageVMFactory).get(HomePageVM::class.java)
    }

    @Inject
    lateinit var navigator: Navigator

    private val homePageRecyclerView
        get() = binding.homePageRecyclerView

    private val adapter: HomePageRecyclerViewAdapter
        get() = homePageRecyclerView.adapter as HomePageRecyclerViewAdapter

    private lateinit var binding: FragmentHomeBinding

    private lateinit var networkStatusLayoutBinding: NetworkStatusLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.app.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        // binding the include layout here with root layout
        networkStatusLayoutBinding = NetworkStatusLayoutBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homePageRecyclerView.adapter = HomePageRecyclerViewAdapter { movie ->
            navigator.openMovieDetails(movie)
        }
        binding.topBar.titleText = "Popular movies"
        viewModel.requestMovieList()
        viewModel.movieListLiveData.observe(viewLifecycleOwner, Observer { resource ->
            val isError = resource.status == ResourceState.ERROR
            networkStatusLayoutBinding.homeProgressBar.isVisible =
                resource.status == ResourceState.LOADING
            networkStatusLayoutBinding.retryButton.isVisible = isError
            networkStatusLayoutBinding.contentErrorMessage.isVisible = isError
            if (resource.status == ResourceState.SUCCESS) {
                adapter.items = resource.data ?: emptyList()
            }
            resource.error?.let { setUpErrorMessage(it) }
        })
        networkStatusLayoutBinding.retryButton.setOnClickListener {
            viewModel.requestMovieList()
        }
    }

    private fun setUpErrorMessage(error: Throwable) {
        val exception = if (error is CompositeException) error.exceptions.first() else error
        val errorMessage = requireContext().getString(
            when (exception) {
                is IOException -> { // since any json parsing error throws IOException
                    if (!requireContext().isInternetConnected) {
                        R.string.error_message_network
                    } else {
                        R.string.error_invalid_response
                    }
                }
                is HttpException -> R.string.error_invalid_response
                else -> R.string.error_message_generic
            }
        )
        networkStatusLayoutBinding.contentErrorMessage.text = errorMessage
    }
}