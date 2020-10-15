package mohsin.reza.movieapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import mohsin.reza.movieapp.network.MovieRepository
import mohsin.reza.movieapp.network.model.Movie
import mohsin.reza.movieapp.network.model.ShelveItem
import mohsin.reza.movieapp.ui.HomePageVM
import mohsin.reza.movieapp.utils.ConnectionUtil
import mohsin.reza.movieapp.utils.OffLineConnectionException
import mohsin.reza.movieapp.utils.scheduler.TestSchedulers
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class HomePageVMTest {
    private val movieRepositoryMock = mockk<MovieRepository>()

    private val connectionUtilMock = mockk<ConnectionUtil>()

    private val viewModel = HomePageVM(movieRepositoryMock, TestSchedulers())

    private val mockMovie = Movie(
        title = "Mulan",
        genreName = "Action",
        voteAverage = 4.6,
        releaseDate = "1-Sep-2020",
        genreIds = listOf()
    )
    private val shelveItem = ShelveItem("Action", listOf(mockMovie))
    private val resultsValid = listOf(shelveItem)

    private val resultsEmpty = listOf<ShelveItem>()

    private val noInternetException = OffLineConnectionException()

    private val error = HttpException(
        Response.error<String>(400, ResponseBody.create(MediaType.parse(""), "content"))
    )

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun givenRepositoryReturnsValidList_whenDataRequested_thenReturnSuccessState() {
        // given
        every { connectionUtilMock.hasInternet() } returns true
        every { movieRepositoryMock.getPopularMoviesList() } returns Observable.just(
            resultsValid
        )

        // when
        viewModel.requestMovieList()

        // then
        viewModel.movieListLiveData.observeForever {
            Assert.assertEquals(resultsValid, it.data)
        }
    }

    @Test
    fun givenRepositoryReturnsEmptyList_whenDataExecuted_thenReturnEmptyState() {
        // given
        every { connectionUtilMock.hasInternet() } returns true
        every { movieRepositoryMock.getPopularMoviesList() } returns Observable.just(
            resultsEmpty
        )

        // when
        // when
        viewModel.requestMovieList()

        // then look for empty List
        viewModel.movieListLiveData.observeForever {
            assert(it.data?.isEmpty() == true)
        }
    }

    @Test
    fun givenRepositoryReturnsError_whenDataRequested_thenReturnErrorState() {
        // given
        every { connectionUtilMock.hasInternet() } returns true
        every { movieRepositoryMock.getPopularMoviesList() } returns Observable.error(error)

        // when
        viewModel.requestMovieList()

        // then
        viewModel.movieListLiveData.observeForever {
            Assert.assertEquals(error, it.error)
        }
    }

    @Test
    fun givenNoInternetConnection_whenDataRequested_thenReturnNoConnectionError() {
        // given
        every { connectionUtilMock.hasInternet() } returns false
        every { movieRepositoryMock.getPopularMoviesList() } returns Observable.error(
            noInternetException
        )

        // when
        viewModel.requestMovieList()

        // then
        viewModel.movieListLiveData.observeForever {
            assert(it.error is OffLineConnectionException)
        }
    }
}