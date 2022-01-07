package com.erykhf.android.brewdogbeergenerator.ui.main.savedbeers

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.erykhf.android.brewdogbeergenerator.R
import com.erykhf.android.brewdogbeergenerator.databinding.FragmentSavedBeerBinding
import com.erykhf.android.brewdogbeergenerator.databinding.FragmentSavedBeerListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class SavedBeerFragment : Fragment(R.layout.fragment_saved_beer_list) {

    private lateinit var binding: FragmentSavedBeerListBinding
    private lateinit var beerAdapter: SavedBeerRecyclerViewAdapter

    private val viewModel: SavedBeersViewModel by  viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBeerListBinding.bind(view)
        setupRecyclerView()

        lifecycleScope.launch {
        beerAdapter.setImageList(viewModel.getAllBeers())

        }

    }


    private fun setupRecyclerView() {
        beerAdapter = SavedBeerRecyclerViewAdapter()
        binding.list.apply {
            adapter = beerAdapter
            layoutManager = GridLayoutManager(requireActivity(), 2)
        }

    }
}