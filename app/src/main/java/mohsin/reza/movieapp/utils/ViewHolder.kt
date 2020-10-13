package mohsin.reza.movieapp.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Abstract class for ViewHolder to use with [ListRecyclerAdapter], subclassed just have to implement onRefreshView method
 */
abstract class ViewHolder<T : Any>(view: View) : RecyclerView.ViewHolder(view), ModelViewHolder<T> {
    override lateinit var model: T
    val context: Context
        get() = itemView.context

    override fun isInitialised() = ::model.isInitialized

    constructor(parent: ViewGroup, @LayoutRes resource: Int) : this(
        LayoutInflater.from(parent.context).inflate(resource, parent, false)
    )
}
