package app.web.drjackycv.features.characters.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import app.web.drjackycv.common.models.Character
import app.web.drjackycv.core.designsystem.load
import app.web.drjackycv.core.designsystem.setOnReactiveClickListener
import app.web.drjackycv.features.characters.R
import app.web.drjackycv.features.characters.databinding.CharacterBinding

class CharactersAdapter(
    private val onItemClick: (Character) -> Unit
) : PagingDataAdapter<Character, CharactersAdapter.CharactersViewHolder>(CHARACTER_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val binding = CharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(character = it, onItemClick = onItemClick)
        }
    }

    override fun getItemViewType(position: Int): Int = R.layout.character

    class CharactersViewHolder(private val binding: CharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character, onItemClick: (Character) -> Unit) {
            binding.tvCharacterId.text = character.id
            binding.imgCharacter.load(
                url = character.image,
                placeholderRes = R.drawable.ic_no_image
            )
            binding.characterContainer.setOnReactiveClickListener {
                onItemClick.invoke(character)
            }
        }

    }

}