package mohsin.reza.movieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.reactivex.exceptions.CompositeException
import kotlinx.android.synthetic.main.fragment_home.content_error_message
import kotlinx.android.synthetic.main.fragment_home.home_page_recycler_view
import kotlinx.android.synthetic.main.fragment_home.home_progress_bar
import kotlinx.android.synthetic.main.fragment_home.retry_button
import mohsin.reza.movieapp.App
import mohsin.reza.movieapp.R
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
        ViewModelProviders.of(this, homePageVMFactory).get(HomePageVM::class.java)
    }

    @Inject
    lateinit var navigator: Navigator

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
        homePageRecyclerView.adapter = HomePageRecyclerViewAdapter { movie ->
            navigator.openMovieDetails(movie)
        }
        viewModel.requestMovieList()
        viewModel.movieListLiveData.observe(viewLifecycleOwner, Observer { resource ->
            val isError = resource.status == ResourceState.ERROR
            home_progress_bar.isVisible = resource.status == ResourceState.LOADING
            retry_button.isVisible = isError
            content_error_message.isVisible = isError
            if (resource.status == ResourceState.SUCCESS) {
                adapter.items = resource.data ?: emptyList()
            }
            resource.error?.let { setUpErrorMessage(it) }
        })
        retry_button.setOnClickListener {
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
        content_error_message.text = errorMessage
    }
}