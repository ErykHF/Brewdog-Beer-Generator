package com.erykhf.android.brewdogbeergenerator.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.erykhf.android.brewdogbeergenerator.networkutils.ConnectionLiveData
import com.erykhf.android.brewdogbeergenerator.utils.GlideImageLoader
import com.erykhf.android.brewdogbeergenerator.utils.ImageLoader
import com.erykhf.android.brewdogbeergenerator.R
import com.erykhf.android.brewdogbeergenerator.databinding.MainFragmentBinding
import com.erykhf.android.brewdogbeergenerator.utils.Util.getProgressDrawable
import com.erykhf.android.brewdogbeergenerator.utils.Util.loadImages
import com.google.android.material.snackbar.Snackbar


class MainFragment : Fragment(R.layout.main_fragment) {

    private lateinit var beerName: TextView
    private lateinit var profileImageView: ImageView
    private lateinit var descriptionResponse: TextView
    private lateinit var firstBrewed: TextView
    private lateinit var tagLine: TextView
    private lateinit var binding: MainFragmentBinding
    private lateinit var connectionLiveData: ConnectionLiveData


    private val imageLoader: ImageLoader by lazy {
        GlideImageLoader(requireActivity())
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentBinding.bind(view)
        beerName = binding.beerName
        profileImageView = binding.mainProfileImage
        descriptionResponse = binding.descriptionResponse
        tagLine = binding.tagLine
        firstBrewed = binding.firstBrewed

        connectionLiveData = ConnectionLiveData(requireContext())

        connectionLiveData.observe(viewLifecycleOwner) { isNetworkAvailable ->

            if (isNetworkAvailable == true) {

                getBeerResponse()

                profileImageView.setOnClickListener {

                    viewModel.getBeerImageResponse()
                    getBeerResponse()
                }
            } else {
                Toast.makeText(requireContext(), "Network Unavailable", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getBeerResponse() {

        viewModel.getBeerImageResponse()
        viewModel.beerData.observe(viewLifecycleOwner) { beerResponse ->

            val noImagePlaceHolder =
                "https://www.allianceplast.com/wp-content/uploads/2017/11/no-image.png"
            val imageUrl = beerResponse?.firstOrNull()?.image_url ?: noImagePlaceHolder

            if (profileImageView != null) {
                val progressDrawable = getProgressDrawable(requireContext())
                profileImageView.loadImages(imageUrl, progressDrawable)
            }
            beerName.text = beerResponse?.firstOrNull()?.name ?: "Unknown"
            descriptionResponse.text = beerResponse?.firstOrNull()?.description
                ?: "No Description"
            firstBrewed.text = ("First brewed: ${beerResponse?.firstOrNull()?.first_brewed}")
                ?: "No Data"
            tagLine.text = beerResponse?.firstOrNull()?.tagline ?: "No tags"
        }

    }

    companion object {
        fun newInstance() = MainFragment()
    }

}