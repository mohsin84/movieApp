package mohsin.reza.movieapp.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mohsin.reza.movieapp.network.model.Movie
import mohsin.reza.movieapp.network.model.ShelveItem
import mohsin.reza.movieapp.utils.safeSize

open class HomePageRecyclerViewAdapter(
    private val onClick: (Movie) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items: List<ShelveItem>? = null
        set(value) {
            field = value
            this.notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ShelveViewHolder(parent, onClick)
    }

    override fun getItemCount(): Int {
        return items.safeSize
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val size = items?.size ?: 1
        items?.get(position % size)?.let { shelveItem ->
            (holder as? ShelveViewHolder)?.updateModel(shelveItem)
        }
    }
}
