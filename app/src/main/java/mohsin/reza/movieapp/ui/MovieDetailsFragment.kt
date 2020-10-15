package mohsin.reza.movieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_movie_details.item_movie_poster_image
import kotlinx.android.synthetic.main.fragment_movie_details.movie_details_text
import kotlinx.android.synthetic.main.fragment_movie_details.movie_title_text
import mohsin.reza.movieapp.App
import mohsin.reza.movieapp.R
import mohsin.reza.movieapp.di.GlideApp
import mohsin.reza.movieapp.network.model.GenreType
import mohsin.reza.movieapp.network.model.Movie
import mohsin.reza.movieapp.utils.IMAGE_BASE_URL
import mohsin.reza.movieapp.utils.POSTER_SIZE
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import java.util.Locale

class MovieDetailsFragment : Fragment() {

    companion object {
        private const val MOVIE_KEY = "movie_key"
        private val DATE_FORMAT =
            DateTimeFormat.forPattern("dd MMM YYYY").withLocale(Locale.ENGLISH)

        fun newInstance(movie: Movie) = MovieDetailsFragment().apply {
            val bundle = Bundle()
            bundle.putParcelable(MOVIE_KEY, movie)
            arguments = bundle
        }
    }

    private val movie: Movie?
        get() = arguments?.getParcelable(MOVIE_KEY)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.app.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageUrl = "$IMAGE_BASE_URL$POSTER_SIZE${movie?.posterPath}"
        var genreType = ""
        val releaseDate = DateTime.parse(movie?.releaseDate ?: "")
        val formattedDate = DateTime(releaseDate, DateTimeZone.getDefault()).toString(DATE_FORMAT)

        loadMoviePoster(url = imageUrl)
        movie_title_text.text = movie?.title ?: movie?.originalTitle
        movie?.genreIds?.map {
            genreType = genreType.plus(GenreType.getTitleFromId(it)).plus(" · ")
        }
        genreType = genreType.removeSuffix(" · ")

        val moveDetailsText =
            "$genreType\nRelease $formattedDate \nVote Average ${movie?.voteAverage ?: 0}"
        movie_details_text.text = moveDetailsText
    }

    private fun loadMoviePoster(url: String) {
        GlideApp.with(item_movie_poster_image)
            .load(url)
            .centerCrop()
            .fallback(R.drawable.place_holder_tile)
            .error(R.drawable.place_holder_tile)
            .into(item_movie_poster_image)
    }
}