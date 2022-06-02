package app.web.drjackycv.features.splash.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import app.web.drjackycv.core.designsystem.setOnReactiveClickListener
import app.web.drjackycv.core.designsystem.viewBinding
import app.web.drjackycv.features.splash.R
import app.web.drjackycv.features.splash.databinding.SplashBinding

class Splash : Fragment(R.layout.splash) {

    private val binding by viewBinding(SplashBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnStart.setOnReactiveClickListener {
            //val action = SplashDirections
            //findNavController().navigate()
        }
    }

}