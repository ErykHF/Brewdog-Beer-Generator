package com.erykhf.android.brewdogbeergenerator.ui.main.beerview

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.erykhf.android.brewdogbeergenerator.R
import com.erykhf.android.brewdogbeergenerator.databinding.BeerViewFragmentBinding
import com.erykhf.android.brewdogbeergenerator.utils.Util
import com.erykhf.android.brewdogbeergenerator.utils.Util.loadImages
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BeerView : Fragment(R.layout.beer_view_fragment) {

    private val args: BeerViewArgs by navArgs()

    private lateinit var binding: BeerViewFragmentBinding
    private val viewModel: BeerViewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BeerViewFragmentBinding.bind(view)
        setHasOptionsMenu(true)

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
                binding.mainProfileImage.loadImages(noImagePlaceHolder)
            } else {
                binding.mainProfileImage.loadImages(args.beerName.image_url)
            }
        }

        ViewCompat.setTransitionName(binding.beerName, "title_${args.imageId}")
        ViewCompat.setTransitionName(binding.tagLine, "duration_${args.imageId}")
        ViewCompat.setTransitionName(binding.mainProfileImage, "thumbnail_${args.imageId}")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete -> {

                val builder = AlertDialog.Builder(requireContext())
                builder.setPositiveButton("Yes") { _, _ ->
                    viewModel.deleteBeer(args.beerName)
                    findNavController().popBackStack(R.id.beerView, true)
                }
                builder.setNegativeButton("Nah") { _, _ -> }
                builder.setTitle("Delete")
                builder.setMessage("Do you want to delete ${args.beerName.name}?")
                builder.create().show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.beer_view_fragment, menu)
    }

}