package mohsin.reza.movieapp.network

import io.reactivex.Observable
import mohsin.reza.movieapp.network.model.GenreType
import mohsin.reza.movieapp.network.model.Genres
import mohsin.reza.movieapp.network.model.Home
import mohsin.reza.movieapp.network.model.Images
import mohsin.reza.movieapp.network.model.Movie
import mohsin.reza.movieapp.network.model.ShelveItem

class MovieRepository(private val movieServices: MovieServices) {
    companion object {
        private const val appId = "f8a36a903e7e2d6585aec1cb27b5a8a5"
        private const val baseURL = "https://api.themoviedb.org/3"
        private const val POPULAR_END = "/movie/popular"
        private const val GENRE_END = "/genre/movie/list"
        private const val CONFIGURATION_END = "/configuration"
        private const val API_KEY_PARAM = "api_key"
        private const val LANGUAGE_PARAM = "&language=en-US&page=1"
        private const val PAGE_PARAM = "&language=en-US&page=1"
    }

    private val movieToShelveMapper: (Home) -> List<ShelveItem> = { home: Home ->
        val hashMovies = HashMap<Int, MutableList<Movie>>()
        val shelveItemList = mutableListOf<ShelveItem>()
        home.results.forEach { movie ->
            movie.genreIds.forEach {
                val list: MutableList<Movie> = hashMovies.get(key = it) ?: mutableListOf()
                list.add(movie)
                // this is the main reason for using a HasMap rather two nested list,
                // I don't need to do a find{} here to decide where to put Movie Item
                hashMovies[it] = list
            }
        }
        // no of keys (genreId) is constant (max 19), based on the genreConfig so this is O(1)
        hashMovies.entries.forEach {
            val genreTitle = GenreType.getTitleFromId(it.key)
            shelveItemList.add(ShelveItem(genreTitle, it.value.toList()))
        }
        shelveItemList.asSequence().sortedBy { it.title }.toList()
    }

    fun getPopularMoviesList(): Observable<List<ShelveItem>> {
        val url = "$baseURL$POPULAR_END?$API_KEY_PARAM=$appId$LANGUAGE_PARAM$PAGE_PARAM"
        return movieServices.getMovieData(url)
            .map(movieToShelveMapper) ?: Observable.just(emptyList())
    }

    fun getImageConfigList(): Observable<Images> {
        val url = "$baseURL$CONFIGURATION_END?$API_KEY_PARAM=$appId"
        return movieServices.getImageConfig(url).map { it.images } ?: Observable.just(Images())
    }

    fun getGenreList(): Observable<List<Genres>> {
        val url = "$baseURL$GENRE_END?$API_KEY_PARAM=$appId$LANGUAGE_PARAM"
        return movieServices.getGenreConfi(url).map { it.genres } ?: Observable.just(emptyList())
    }
}