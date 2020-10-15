package mohsin.reza.movieapp.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mohsin.reza.movieapp.network.model.Movie
import mohsin.reza.movieapp.utils.RecyclerViewAdapter

open class ShelveRecyclerViewAdapter(
    private val onClick: (Movie) -> Unit
) : RecyclerViewAdapter<Movie, ShelveMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ShelveMovieViewHolder(parent, onClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val size = items?.size ?: 1
        items?.get(position % size)?.let { movie ->
            (holder as? ShelveMovieViewHolder)?.updateModel(movie)
        }
    }
}
