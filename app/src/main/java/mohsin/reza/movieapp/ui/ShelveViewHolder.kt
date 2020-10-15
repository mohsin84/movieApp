package mohsin.reza.movieapp.ui

import android.os.Parcelable
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_shelve_item.view.item_shelve_recycler_view
import kotlinx.android.synthetic.main.item_shelve_item.view.item_title_text
import mohsin.reza.movieapp.R
import mohsin.reza.movieapp.network.model.Movie
import mohsin.reza.movieapp.utils.ScreenUtils
import mohsin.reza.movieapp.utils.ViewHolder

class ShelveViewHolder constructor(
    parent: ViewGroup,
    onClick: (Movie) -> Unit
) : ViewHolder<ShelveViewModel>(parent, R.layout.item_shelve_item) {

    private val recyclerView = itemView.item_shelve_recycler_view
    private val titleText = itemView.item_title_text
    private val adapter: ShelveRecyclerViewAdapter
        get() = recyclerView.adapter as ShelveRecyclerViewAdapter
    private val itemPadding = ScreenUtils.getDimen(R.dimen.spacing_s)
    private var isSavingState = false
    private val selectedStateObserver = Observer<Parcelable> { if (!isSavingState) refreshState() }
    private val recyclerViewScroller = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            isSavingState = true
            model.selectedScrollState.value = recyclerView.layoutManager!!.onSaveInstanceState()
            isSavingState = false
        }
    }

    init {
        recyclerView.adapter = ShelveRecyclerViewAdapter(onClick)
        recyclerView.addOnScrollListener(recyclerViewScroller)
    }

    override fun onRefreshView(model: ShelveViewModel) {
        val shelve = model.shelveItem
        titleText.text = shelve.title
        adapter.items = shelve.movieList
        val visibility = shelve.movieList.isNotEmpty()
        itemView.isVisible = visibility
        titleText.isVisible = visibility
    }

    override fun onAttach() {
        super.onAttach()
        model.selectedScrollState.observeForever(selectedStateObserver)
    }

    override fun onDetach() {
        super.onDetach()
        model.selectedScrollState.removeObserver(selectedStateObserver)
    }

    private fun refreshState() {
        val state = model.selectedScrollState.value
        if (state != null) {
            recyclerView.layoutManager?.onRestoreInstanceState(state)
        } else {
            (recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                0,
                itemPadding
            )
        }
    }
}