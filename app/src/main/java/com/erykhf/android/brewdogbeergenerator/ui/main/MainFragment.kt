package com.erykhf.android.brewdogbeergenerator.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.*
import android.net.ConnectivityManager.NetworkCallback
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.erykhf.android.brewdogbeergenerator.GlideImageLoader
import com.erykhf.android.brewdogbeergenerator.ImageLoader
import com.erykhf.android.brewdogbeergenerator.R
import com.erykhf.android.brewdogbeergenerator.api.RetrofitService
import com.erykhf.android.brewdogbeergenerator.databinding.MainFragmentBinding
import com.erykhf.android.brewdogbeergenerator.model.BeerData
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private const val TAG = "MainActivity"

class MainFragment : Fragment(R.layout.main_fragment) {

    private lateinit var beerName: TextView
    private lateinit var profileImageView: ImageView
    private lateinit var descriptionResponse: TextView
    private lateinit var firstBrewed: TextView
    private lateinit var tagLine: TextView
    private var isNetworkConnected = false

    private lateinit var binding: MainFragmentBinding

    private val imageLoader: ImageLoader by lazy {
        GlideImageLoader(requireActivity())
    }

    private lateinit var viewModel: MainViewModel

//    private fun getBeerResponse() {
//
//        val punkApiLiveData: LiveData<List<BeerData>> = RetrofitService().getBeerImageResponse()
//        punkApiLiveData.observe(requireActivity(), Observer { beerResponse ->
//            Log.d(TAG, "onCreateView: $beerResponse")
//
//            val noImagePlaceHolder =
//                "https://www.allianceplast.com/wp-content/uploads/2017/11/no-image.png"
//            val imageUrl = beerResponse?.firstOrNull()?.image_url ?: noImagePlaceHolder
//
//            if (profileImageView != null) {
//                imageLoader.loadImage(imageUrl, profileImageView)
//            }
//            beerName.text = beerResponse?.firstOrNull()?.name ?: "Unknown"
//            descriptionResponse.text = beerResponse?.firstOrNull()?.description
//                ?: "No Description"
//            firstBrewed.text = ("First brewed: ${beerResponse?.firstOrNull()?.first_brewed}")
//                ?: "No Data"
//            tagLine.text = beerResponse?.firstOrNull()?.tagline ?: "No tags"
//        })
//    }


    //    To Use with ViewModel when you figure out the OnClick/ViewModel (Why it's not updating).
    private fun getBeerResponse() {

        viewModel.beerItemLiveData.observe(viewLifecycleOwner, Observer { beerResponse ->
            Log.d(TAG, "onCreateView: $beerResponse")

            viewModel.refresh()

            val noImagePlaceHolder =
                "https://www.allianceplast.com/wp-content/uploads/2017/11/no-image.png"
            val imageUrl = beerResponse?.firstOrNull()?.image_url ?: noImagePlaceHolder

            if (profileImageView != null) {
                imageLoader.loadImage(imageUrl, profileImageView)
            }
            beerName.text = beerResponse?.firstOrNull()?.name ?: "Unknown"
            descriptionResponse.text = beerResponse?.firstOrNull()?.description
                ?: "No Description"
            firstBrewed.text = ("First brewed: ${beerResponse?.firstOrNull()?.first_brewed}")
                ?: "No Data"
            tagLine.text = beerResponse?.firstOrNull()?.tagline ?: "No tags"
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        registerNetworkCallback()
        return inflater.inflate(R.layout.main_fragment, container, false)

    }

    fun snackFunction() {
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
                (this.context?.getSystemService(Context.WIFI_SERVICE) as? WifiManager)?.apply {
                    isWifiEnabled = true /*or false*/
                }
            }
        })
        snack.show()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        beerName = binding.beerName
        profileImageView = binding.mainProfileImage
        descriptionResponse = binding.descriptionResponse
        tagLine = binding.tagLine
        firstBrewed = binding.firstBrewed


        if (isNetworkConnected != true) {
            Toast.makeText(requireContext(), "Network Not Connected", Toast.LENGTH_LONG).show()
            snackFunction()
        }

        profileImageView.setOnClickListener {
            if (isNetworkConnected != true) {
                snackFunction()
            } else {
                getBeerResponse()
            }
        }
    }

    private fun registerNetworkCallback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                val connectivityManager =
                    requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val builder = NetworkRequest.Builder()
                builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
                builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)

                connectivityManager.registerDefaultNetworkCallback(object : NetworkCallback() {

                    override fun onAvailable(network: Network) {
                        Toast.makeText(requireContext(), "Network Connected", Toast.LENGTH_LONG)
                            .show()
                        isNetworkConnected = true // Global Static Variable
                        lifecycleScope.launch {
                            withContext(Dispatchers.Main) {
                                getBeerResponse()
                            }
                        }
                    }

                    override fun onLost(network: Network) {
                        Toast.makeText(
                            requireContext(),
                            "Network Lost",
                            Toast.LENGTH_LONG
                        ).show()

                        isNetworkConnected = false // Global Static Variable
                        lifecycleScope.launch {
                            withContext(Dispatchers.Main) {
                                snackFunction()
                            }
                        }

                    }
                }

                )
                isNetworkConnected = false
            } catch (e: Exception) {
                isNetworkConnected = false
            }
        } else {
            (this.context?.getSystemService(Context.WIFI_SERVICE) as? WifiManager)?.apply {
                isWifiEnabled = true /*or false*/
            }

        }
    }


    companion object {
        fun newInstance() = MainFragment()
    }

}