package app.web.drjackycv.features.characters.presentation

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import app.web.drjackycv.features.characters.databinding.LoadingRowBinding

class LoadingStateViewHolder(private val itemBinding: LoadingRowBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(loadState: LoadState) {
        itemBinding.itemLoadingRowContainer.isVisible = loadState is LoadState.Loading
    }

}