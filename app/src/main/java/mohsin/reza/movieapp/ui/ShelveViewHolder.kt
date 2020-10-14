package mohsin.reza.movieapp.ui

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.item_shelve_item.view.item_shelve_recycler_view
import kotlinx.android.synthetic.main.item_shelve_item.view.item_title_text
import mohsin.reza.movieapp.R
import mohsin.reza.movieapp.network.model.GenreType
import mohsin.reza.movieapp.network.model.Movie
import mohsin.reza.movieapp.network.model.ShelveItem
import mohsin.reza.movieapp.utils.ScreenUtils
import mohsin.reza.movieapp.utils.ViewHolder
import kotlin.math.ceil

class ShelveViewHolder constructor(
    parent: ViewGroup,
    onClick: (Movie) -> Unit
) : ViewHolder<ShelveItem>(parent, R.layout.item_shelve_item) {

    private val recyclerView = itemView.item_shelve_recycler_view
    private val titleText = itemView.item_title_text
    private val adapter: ShelveRecyclerViewAdapter
        get() = recyclerView.adapter as ShelveRecyclerViewAdapter
    private val itemPadding = ScreenUtils.getDimen(R.dimen.spacing_s)

    init {
        val tileWidth = ScreenUtils.getDimen(R.dimen.shelve_item_width)
        val tilesOnPageCnt = ceil(ScreenUtils.screenWidth.toDouble() / tileWidth).toInt()
        recyclerView.adapter = ShelveRecyclerViewAdapter(onClick)
    }

    override fun onRefreshView(model: ShelveItem) {
        val genreType: GenreType? = GenreType.values().find { it.Id == model.genreId }
        titleText.text = genreType?.genreTitle ?: GenreType.UNKNOWN.genreTitle
        adapter.items = model.movieList
        val visibility = model.movieList.isNotEmpty()
        itemView.isVisible = visibility
        titleText.isVisible = visibility
        refreshState()
    }

    private fun refreshState() {
        val data = model.movieList
        val infinite = data.isNotEmpty() && adapter.itemCount > data.size
        (recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
            0,
            itemPadding
        )
        recyclerView.setPadding(itemPadding, 0, itemPadding, 0)
    }
}