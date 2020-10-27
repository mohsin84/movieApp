package mohsin.reza.movieapp

import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import mohsin.reza.movieapp.network.MovieRepository
import mohsin.reza.movieapp.network.MovieServices
import mohsin.reza.movieapp.network.model.Home
import mohsin.reza.movieapp.network.model.Movie
import mohsin.reza.movieapp.network.model.ShelveItem
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class MovieRepositoryTest {

    private val emptyHome = Home(1, 1000, 500, listOf())
    private val movieServicesMock = mockk<MovieServices>(relaxed = true)
    private val movieRepository by lazy {
        MovieRepository(movieServicesMock)
    }
    private val movie1 = Movie(title = "Mulan", genreIds = listOf(12, 16, 28, 35, 53, 80, 878))
    private val movie2 = Movie(title = "Ava", genreIds = listOf(14, 16, 27, 35))
    private val movie3 = Movie(title = "Enola Holmes", genreIds = listOf(12, 28, 16, 878))
    private val movie4 = Movie(title = "Hubie", genreIds = listOf(35, 53, 80))
    private val movie5 = Movie(title = "Mr. Bean", genreIds = listOf(16, 35, 80, 9648))
    private val movie6 = Movie(title = "Spider Man", genreIds = listOf(27, 878))
    private val movie7 = Movie(title = "Shutter Island", genreIds = listOf(12, 16, 28, 53))

    private val homeMock: Home =
        Home(1, 1000, 500, listOf(movie1, movie2, movie3, movie4, movie5, movie6, movie7))

    private val errorResult = HttpException(
        Response.error<String>(400, ResponseBody.create("".toMediaTypeOrNull(), "content"))
    )

    @Test
    fun givenServiceReturnMovieList_whenDataRequested_thenVerifyDataIsCorrect() {
        // given
        every { movieServicesMock.getMovieData(any()) } returns Observable.just(homeMock)

        // when
        val list: List<ShelveItem> = movieRepository.getPopularMoviesList().test().values()[0]

        // then
        // verify list is not empty
        assertEquals(true, list.isNotEmpty())
        // verify list size
        assertEquals(10, list.size)

        // verify list is sorted and content is correct
        // verify first item
        val firstItem = list[0]
        assertEquals("Action", firstItem.title)
        assertEquals(3, firstItem.movieList.size)
        // verify second movie of action genre
        assertEquals("Enola Holmes", firstItem.movieList[1].title)

        // verify third item
        val thirdItem = list[2]
        assertEquals("Animation", thirdItem.title)
        assertEquals(5, thirdItem.movieList.size)
        // verify Second movie of Animation genre
        assertEquals("Ava", thirdItem.movieList[1].title)

        // verify last item
        val lastItem = list[list.size - 1]
        assertEquals("Thriller", lastItem.title)
        assertEquals(3, lastItem.movieList.size)
        // verify third movie of Thriller genre
        assertEquals("Shutter Island", lastItem.movieList[2].title)
    }

    @Test
    fun givenServiceReturnEmptyList_whenDataRequested_thenVerifyDataIsEmpty() {
        // given
        every { movieServicesMock.getMovieData(any()) } returns Observable.just(emptyHome)

        // when
        val list: List<ShelveItem> = movieRepository.getPopularMoviesList().test().values()[0]

        // then
        val size = list.size
        // verify list is  empty
        assertEquals(true, list.isEmpty())
        // verify list size
        assertEquals(0, size)
    }

    @Test
    fun givenServiceReturnsError_whenDataRequested_thenVerifyErrorState() {
        // given
        every { movieServicesMock.getMovieData(any()) } returns Observable.error(errorResult)

        // then
        movieRepository.getPopularMoviesList().test().assertError(errorResult)
    }
}