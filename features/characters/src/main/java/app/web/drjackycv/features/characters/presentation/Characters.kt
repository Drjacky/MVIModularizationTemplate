package app.web.drjackycv.features.characters.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import app.web.drjackycv.core.designsystem.viewBinding
import app.web.drjackycv.features.characters.R
import app.web.drjackycv.features.characters.databinding.CharactersBinding

class Characters : Fragment(R.layout.characters) {

    private val binding by viewBinding(CharactersBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
    }

}