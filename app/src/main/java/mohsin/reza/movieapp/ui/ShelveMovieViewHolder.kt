package mohsin.reza.movieapp.ui

import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import mohsin.reza.movieapp.R
import mohsin.reza.movieapp.databinding.ItemShelveMovieItemBinding
import mohsin.reza.movieapp.di.GlideApp
import mohsin.reza.movieapp.network.model.Movie
import mohsin.reza.movieapp.utils.IMAGE_BASE_URL
import mohsin.reza.movieapp.utils.POSTER_SIZE
import mohsin.reza.movieapp.utils.ViewHolder

class ShelveMovieViewHolder constructor(
    parent: ViewGroup,
    private val onClick: (Movie) -> Unit
) : ViewHolder<Movie, ItemShelveMovieItemBinding>(parent, R.layout.item_shelve_movie_item) {
    override var binding = ItemShelveMovieItemBinding.bind(itemView)
    private val movieImageView: ImageView = binding.movieItemImageView

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