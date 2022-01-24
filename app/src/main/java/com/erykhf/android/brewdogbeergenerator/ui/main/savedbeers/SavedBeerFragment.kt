package com.erykhf.android.brewdogbeergenerator.ui.main.savedbeers

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.erykhf.android.brewdogbeergenerator.R
import com.erykhf.android.brewdogbeergenerator.database.SortOrder
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

    private val viewModel: SavedBeersViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBeerListBinding.bind(view)
        setupRecyclerView()
        setHasOptionsMenu(true)

        lifecycleScope.launch {

            viewModel.readAllData.observe(viewLifecycleOwner) { beer ->
                beerAdapter.setBeerList(beer)

                beerAdapter.onLongClick {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setPositiveButton("Yes") { _, _ ->
                        viewModel.deleteBeer(it)
                        beerAdapter.notifyDataSetChanged()

                    }
                    builder.setNegativeButton("Nah") { _, _ -> }
                    builder.setTitle("Delete")
                    builder.setMessage("Do you want to delete ${it.name}?")
                    builder.create().show()

                }
            }

//            beerAdapter.setOnItemClickListener {
//
//                val transitionName = getString(R.string.details_transition_name)
//                val extras = FragmentNavigator.Extras.Builder()
//                    .addSharedElement(binding.root.findViewById(R.id.card), "image")
//                    .build()
//                val navDirections =
//                    SavedBeerFragmentDirections.actionSavedBeerFragmentToBeerView(it)
//                val navController = findNavController()
//                navController.navigate(navDirections, extras)
//            }
        }
    }


    private fun setupRecyclerView() {
        beerAdapter = SavedBeerRecyclerViewAdapter()
        binding.list.apply {
            this.adapter = beerAdapter
            postponeEnterTransition()
            layoutManager = GridLayoutManager(activity, 2)
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }

        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.saved_beer_fragment, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.name -> {
                viewModel.onSortOrderSelected(SortOrder.BY_NAME)
                true
            }

            R.id.date -> {
                viewModel.onSortOrderSelected(SortOrder.BY_DATE)
                true
            }

            R.id.delete -> {
                viewModel.deleteAll()
                true
            }


            else -> super.onOptionsItemSelected(item)
        }
    }
}