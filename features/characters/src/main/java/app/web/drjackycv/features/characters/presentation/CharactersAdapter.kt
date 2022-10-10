package app.web.drjackycv.features.characters.presentation

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.paging.PagingDataAdapter
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import app.web.drjackycv.common.models.fragment.CharacterDetail
import app.web.drjackycv.core.designsystem.load
import app.web.drjackycv.core.designsystem.setOnReactiveClickListener
import app.web.drjackycv.features.characters.R
import app.web.drjackycv.features.characters.databinding.CharacterRowBinding
import java.util.*


class CharactersAdapter(
    private val onItemClick: (CharacterDetail, CharacterRowBinding) -> Unit,
) : PagingDataAdapter<CharacterDetail, CharactersAdapter.CharactersViewHolder>(
    CHARACTER_DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val binding =
            CharacterRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(character = it, onItemClick = onItemClick)
        }
    }

    override fun getItemViewType(position: Int): Int = R.layout.character_row

    class CharactersViewHolder(private val binding: CharacterRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            character: CharacterDetail,
            onItemClick: (CharacterDetail, CharacterRowBinding) -> Unit
        ) =
            with(itemView) {
                binding.tvId.text = character.id
                binding.tvId.transitionName = character.id
                binding.tvName.text = character.name
                binding.tvName.transitionName = character.name
                binding.tvSpecies.transitionName = character.species
                binding.tvSpecies.text = character.species
                binding.tvGender.transitionName = character.gender
                binding.tvGender.text = character.gender
                binding.imgCharacter.load(
                    url = character.image,
                    placeholderRes = app.web.drjackycv.core.designsystem.R.drawable.ic_no_image,
                    action = { paintRow(from = it) }
                )
                binding.imgCharacter.transitionName = character.image
                itemView.setOnReactiveClickListener {
                    onItemClick(character, binding)
                }
            }

        private fun paintRow(from: Drawable) {
            val bitmap = from.toBitmap()
            val vibrant = Palette.from(bitmap)
                .generate()
                .lightVibrantSwatch

            vibrant?.let {
                val hexColor = String.format(Locale.getDefault(), "#%06X", 0xFFFFFF and it.rgb)
                binding.characterRow.setBackgroundColor(Color.parseColor(hexColor))
            }
        }

    }

}