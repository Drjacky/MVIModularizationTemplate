package app.web.drjackycv.features.characters.presentation

import androidx.recyclerview.widget.DiffUtil
import app.web.drjackycv.common.models.Character

val CHARACTER_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Character>() {

    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }

}