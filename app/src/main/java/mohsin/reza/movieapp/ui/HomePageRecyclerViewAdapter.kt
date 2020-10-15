package mohsin.reza.movieapp.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mohsin.reza.movieapp.network.model.Movie
import mohsin.reza.movieapp.utils.RecyclerViewAdapter

open class HomePageRecyclerViewAdapter(
    private val onClick: (Movie) -> Unit
) : RecyclerViewAdapter<ShelveViewModel, ShelveViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ShelveViewHolder(parent, onClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val size = items?.size ?: 1
        items?.get(position % size)?.let { shelveItem ->
            (holder as? ShelveViewHolder)?.updateModel(shelveItem)
        }
    }
}
