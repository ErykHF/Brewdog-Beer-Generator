package com.erykhf.android.brewdogbeergenerator.ui.main.beerview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.erykhf.android.brewdogbeergenerator.R
import com.erykhf.android.brewdogbeergenerator.databinding.BeerViewFragmentBinding
import com.erykhf.android.brewdogbeergenerator.utils.Util
import com.erykhf.android.brewdogbeergenerator.utils.Util.loadImages
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BeerView : Fragment(R.layout.beer_view_fragment) {

    private val args: BeerViewArgs by navArgs()

    private lateinit var binding: BeerViewFragmentBinding
    private val viewModel: BeerViewViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BeerViewFragmentBinding.bind(view)

        lifecycleScope.launch {

            viewModel.getBeer(args.beerName.name)

            binding.beerName.text = args.beerName.name
            binding.descriptionResponse.text = args.beerName.description
            binding.firstBrewed.text = args.beerName.first_brewed
            binding.tagLine.text = args.beerName.tagline
            val noImagePlaceHolder =
                "https://www.allianceplast.com/wp-content/uploads/2017/11/no-image.png"
            val progressDrawable = Util.getProgressDrawable(requireContext())

            if (args.beerName.image_url.isNullOrBlank()) {
                binding.mainProfileImage.loadImages(noImagePlaceHolder, progressDrawable)
            } else {
                binding.mainProfileImage.loadImages(args.beerName.image_url, progressDrawable)
            }

        }

    }

}