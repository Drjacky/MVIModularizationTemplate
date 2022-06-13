package app.web.drjackycv.features.characters.presentation

import androidx.recyclerview.widget.DiffUtil
import app.web.drjackycv.common.models.fragment.CharacterDetail

val CHARACTER_DIFF_CALLBACK = object : DiffUtil.ItemCallback<CharacterDetail>() {

    override fun areItemsTheSame(oldItem: CharacterDetail, newItem: CharacterDetail): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CharacterDetail, newItem: CharacterDetail): Boolean {
        return oldItem == newItem
    }

}