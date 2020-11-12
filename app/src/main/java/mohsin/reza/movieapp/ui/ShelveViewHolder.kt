package mohsin.reza.movieapp.ui

import android.os.Parcelable
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mohsin.reza.movieapp.R
import mohsin.reza.movieapp.databinding.ItemShelveItemBinding
import mohsin.reza.movieapp.network.model.Movie
import mohsin.reza.movieapp.utils.ScreenUtils
import mohsin.reza.movieapp.utils.ViewHolder

class ShelveViewHolder constructor(
    parent: ViewGroup,
    onClick: (Movie) -> Unit
) : ViewHolder<ShelveViewModel, ItemShelveItemBinding>(parent, R.layout.item_shelve_item) {

    override var binding = ItemShelveItemBinding.bind(itemView)
    private val recyclerView = binding.itemShelveRecyclerView
    private val titleText = binding.itemTitleText
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
        binding.itemShelveRecyclerView.adapter = ShelveRecyclerViewAdapter(onClick)
        binding.itemShelveRecyclerView.addOnScrollListener(recyclerViewScroller)
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