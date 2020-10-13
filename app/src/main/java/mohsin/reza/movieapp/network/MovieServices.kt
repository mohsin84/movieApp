package mohsin.reza.movieapp.network

import io.reactivex.Observable
import mohsin.reza.movieapp.network.model.GenreResult
import mohsin.reza.movieapp.network.model.Home
import mohsin.reza.movieapp.network.model.ImageResult
import retrofit2.http.GET
import retrofit2.http.Url

interface MovieServices {
    @GET
    fun getMovieData(@Url url: String): Observable<Home>

    @GET
    fun getImageConfig(@Url url: String): Observable<ImageResult>

    @GET
    fun getGenreConfi(@Url url: String): Observable<GenreResult>
}