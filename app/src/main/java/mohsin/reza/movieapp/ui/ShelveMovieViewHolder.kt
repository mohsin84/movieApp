package mohsin.reza.movieapp.ui

import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_shelve_movie_item.view.movie_item_image_view
import mohsin.reza.movieapp.R
import mohsin.reza.movieapp.di.GlideApp
import mohsin.reza.movieapp.network.model.Movie
import mohsin.reza.movieapp.utils.IMAGE_BASE_URL
import mohsin.reza.movieapp.utils.POSTER_SIZE
import mohsin.reza.movieapp.utils.ViewHolder

class ShelveMovieViewHolder constructor(
    parent: ViewGroup,
    private val onClick: (Movie) -> Unit
) : ViewHolder<Movie>(parent, R.layout.item_shelve_movie_item) {

    private val movieImageView: ImageView = itemView.movie_item_image_view

    init {
        itemView.setOnClickListener {
            onClick(model)
        }
    }

    override fun onRefreshView(model: Movie) {
        movieImageView.contentDescription = model.title
    }

    override fun onAttach() {
        super.onAttach()
        val imageUrl = "$IMAGE_BASE_URL$POSTER_SIZE${model.posterPath}"
        GlideApp.with(movieImageView)
            .load(imageUrl)
            .centerCrop()
            .fallback(R.drawable.place_holder_tile)
            .error(R.drawable.place_holder_tile)
            .into(movieImageView)
    }

    override fun onDetach() {
        super.onDetach()
        Glide.with(context).clear(movieImageView)
    }
}