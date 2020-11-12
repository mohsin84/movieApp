package mohsin.reza.movieapp.utils

import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewAdapter<T : Any, VH : ViewHolder<*, *>> :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items: List<T>? = null
        set(value) {
            field = value
            this.notifyDataSetChanged()
        }

    override fun getItemCount(): Int = items.safeSize

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        if (holder is DetachableViewHolder) {
            holder.detach()
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder is DetachableViewHolder) {
            holder.attach()
        }
    }
}