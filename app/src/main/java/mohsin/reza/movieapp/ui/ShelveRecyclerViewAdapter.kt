package mohsin.reza.movieapp.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mohsin.reza.movieapp.network.model.Movie
import mohsin.reza.movieapp.utils.DetachableViewHolder
import mohsin.reza.movieapp.utils.safeSize

open class ShelveRecyclerViewAdapter(
    private val onClick: (Movie) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items: List<Movie>? = null
        set(value) {
            field = value
            this.notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ShelveMovieViewHolder(parent, onClick)
    }

    override fun getItemCount(): Int {
        return items.safeSize
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val size = items?.size ?: 1
        items?.get(position % size)?.let { movie ->
            (holder as? ShelveMovieViewHolder)?.updateModel(movie)
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder is DetachableViewHolder) {
            holder.attach()
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        if (holder is DetachableViewHolder) {
            holder.detach()
        }
    }
}
