package mohsin.reza.movieapp.ui

import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.item_shelve_movie_item.view.*
import mohsin.reza.movieapp.R
import mohsin.reza.movieapp.network.model.Movie
import mohsin.reza.movieapp.utils.ViewHolder

class ShelveMovieViewHolder constructor(
    parent: ViewGroup,
    private val onClick: (Movie) -> Unit
) : ViewHolder<Movie>(parent, R.layout.item_shelve_movie_item) {

    val movieImageView: ImageView = itemView.movie_item_image_view

    init {
        itemView.setOnClickListener {
            onClick.invoke(model)
        }
    }

    override fun onRefreshView(model: Movie) {
        TODO("Not yet implemented")
    }

    override fun onAttach() {
        super.onAttach()
        Glide.with(movieImageView)
            .load("imageUrl")
            .transition(DrawableTransitionOptions.withCrossFade())
//            .fallback()
//            .error()
            .into(movieImageView)
    }

    override fun onDetach() {
        super.onDetach()
        Glide.with(context).clear(movieImageView)
    }
}