package mohsin.reza.movieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import mohsin.reza.movieapp.App
import mohsin.reza.movieapp.R
import mohsin.reza.movieapp.databinding.FragmentMovieDetailsBinding
import mohsin.reza.movieapp.di.GlideApp
import mohsin.reza.movieapp.network.model.GenreType
import mohsin.reza.movieapp.network.model.Movie
import mohsin.reza.movieapp.utils.IMAGE_BASE_URL
import mohsin.reza.movieapp.utils.POSTER_SIZE
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import java.util.*

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

    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.app.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageUrl = "$IMAGE_BASE_URL$POSTER_SIZE${movie?.posterPath}"
        var genreType = ""
        val releaseDate = DateTime.parse(movie?.releaseDate ?: "")
        val formattedDate = DateTime(releaseDate, DateTimeZone.getDefault()).toString(DATE_FORMAT)
        val title = movie?.title ?: movie?.originalTitle ?: ""

        loadMoviePoster(url = imageUrl)
        binding.topBar.titleText = title
        binding.topBar.backButtonVisibility = true
        binding.movieTitleText.text = title
        movie?.genreIds?.map {
            genreType = genreType.plus(GenreType.getTitleFromId(it)).plus(" · ")
        }
        genreType = genreType.removeSuffix(" · ")

        val moveDetailsText =
            "$genreType\nRelease $formattedDate \nVote Average ${movie?.voteAverage ?: 0}"
        binding.movieDetailsText.text = moveDetailsText
    }

    private fun loadMoviePoster(url: String) {
        val imageView = binding.itemMoviePosterImage
        GlideApp.with(imageView)
            .load(url)
            .centerCrop()
            .fallback(R.drawable.place_holder_tile)
            .error(R.drawable.place_holder_tile)
            .into(imageView)
    }
}