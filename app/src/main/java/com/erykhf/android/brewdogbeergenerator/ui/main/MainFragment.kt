package com.erykhf.android.brewdogbeergenerator.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.erykhf.android.brewdogbeergenerator.networkutils.ConnectionLiveData
import com.erykhf.android.brewdogbeergenerator.R
import com.erykhf.android.brewdogbeergenerator.databinding.MainFragmentBinding
import com.erykhf.android.brewdogbeergenerator.utils.Util.getProgressDrawable
import com.erykhf.android.brewdogbeergenerator.utils.Util.loadImages
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    private lateinit var binding: MainFragmentBinding
    private lateinit var connectionLiveData: ConnectionLiveData
    private val viewModel: MainViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentBinding.bind(view)

        connectionLiveData = ConnectionLiveData(requireContext())

        binding.floatingActionButton2?.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_savedBeerFragment)
        }

        connectionLiveData.observe(viewLifecycleOwner) { isNetworkAvailable ->

            if (isNetworkAvailable == true) {

                getBeerResponse()

                binding.mainProfileImage.setOnClickListener {

                    viewModel.refresh()
                    getBeerResponse()
                }
            } else {
                snackFunction()
                Toast.makeText(requireContext(), "Network Unavailable", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.error.observe(viewLifecycleOwner){
            Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun getBeerResponse() {

        viewModel.beerItemLiveData.observe(viewLifecycleOwner) { beerResponse ->

            binding.floatingActionButton?.setOnClickListener {
                viewModel.saveBeer(beerResponse)
            }

            beerResponse?.let {
                val noImagePlaceHolder =
                    "https://www.allianceplast.com/wp-content/uploads/2017/11/no-image.png"
                val imageUrl = beerResponse.firstOrNull()?.image_url ?: noImagePlaceHolder
                val progressDrawable = getProgressDrawable(requireContext())

                binding.mainProfileImage.loadImages(imageUrl, progressDrawable)
                binding.beerName.text = beerResponse.firstOrNull()?.name ?: "Unknown"
                binding.descriptionResponse.text =
                    beerResponse.firstOrNull()?.description ?: "No Description"
                binding.firstBrewed.text =
                    ("First brewed: ${beerResponse.firstOrNull()?.first_brewed}")
                binding.tagLine.text = beerResponse.firstOrNull()?.tagline ?: "No tags"
            }
        }
    }

    private fun snackFunction() {
        val snack: Snackbar =
            Snackbar.make(requireView(), "No internet connection", Snackbar.LENGTH_LONG)
        val view1 = snack.view
        val params = view1.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view1.layoutParams = params
        snack.setTextColor(Color.CYAN)
        snack.setActionTextColor(Color.GREEN)
        snack.setBackgroundTint(Color.BLACK)
        snack.setAction("Connect", View.OnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val panelIntent = Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
                startActivityForResult(panelIntent, 0)
            } else {
                // use previous solution, add appropriate permissions to AndroidManifest file (see answers above)
                (this.context?.applicationContext
                    ?.getSystemService(Context.WIFI_SERVICE) as? WifiManager)?.apply {
                    isWifiEnabled = true /*or false*/
                }
            }
        })
        snack.show()
    }


}