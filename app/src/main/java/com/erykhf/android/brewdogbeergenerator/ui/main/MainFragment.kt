package com.erykhf.android.brewdogbeergenerator.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.erykhf.android.brewdogbeergenerator.GlideImageLoader
import com.erykhf.android.brewdogbeergenerator.ImageLoader
import com.erykhf.android.brewdogbeergenerator.R
import com.erykhf.android.brewdogbeergenerator.api.RetrofitService
import com.erykhf.android.brewdogbeergenerator.model.BeerData

private const val BASE_URL = "https://api.punkapi.com/v2/beers/"
private const val TAG = "MainActivity"

class MainFragment : Fragment() {

    private var beerName: TextView? = view?.findViewById(R.id.beer_name)
    private var profileImageView: ImageView? = view?.findViewById(R.id.main_profile_image)
    private var descriptionResponse: TextView? = view?.findViewById(R.id.description_response)
    private var firstBrewed: TextView? = view?.findViewById(R.id.first_brewed)
    private var tagLine: TextView? = view?.findViewById(R.id.tag_line)


    private val imageLoader: ImageLoader by lazy {
        GlideImageLoader(requireActivity())
    }

    private lateinit var viewModel: MainViewModel

    private fun getBeerResponse() {

        val punkApiLiveData: LiveData<List<BeerData>> = RetrofitService().getBeerImageResponse()
        punkApiLiveData.observe(requireActivity(), Observer { beerResponse ->
            Log.d(TAG, "onCreateView: $beerResponse")

            val noImagePlaceHolder = "https://www.allianceplast.com/wp-content/uploads/2017/11/no-image.png"
            val imageUrl = beerResponse?.firstOrNull()?.image_url ?: noImagePlaceHolder

            if (profileImageView != null) {
                imageLoader.loadImage(imageUrl, profileImageView!!)
            }
            beerName?.text = beerResponse?.firstOrNull()?.name ?: "Unknown"
            descriptionResponse?.text = beerResponse?.firstOrNull()?.description
                    ?: "No Description"
            firstBrewed?.text = ("First brewed: ${beerResponse?.firstOrNull()?.first_brewed}")
                    ?: "No Data"
            tagLine?.text = beerResponse?.firstOrNull()?.tagline ?: "No tags"
        })
    }

//    private fun getBeerResponseViewModel(){
//
//        viewModel.beerItemLiveData.observe(viewLifecycleOwner, Observer { beerResponse ->
//            Log.d(TAG, "onCreateView: $beerResponse")
//
//            val noImagePlaceHolder = "https://www.allianceplast.com/wp-content/uploads/2017/11/no-image.png"
//            val imageUrl = beerResponse?.firstOrNull()?.image_url ?: noImagePlaceHolder
//
//            if (profileImageView != null) {
//                imageLoader.loadImage(imageUrl, profileImageView!!)
//            }
//            beerName?.text = beerResponse?.firstOrNull()?.name ?: "Unknown"
//            descriptionResponse?.text = beerResponse?.firstOrNull()?.description
//                    ?: "No Description"
//            firstBrewed?.text = ("First brewed: ${beerResponse?.firstOrNull()?.first_brewed}")
//                    ?: "No Data"
//            tagLine?.text = beerResponse?.firstOrNull()?.tagline ?: "No tags"
//        })
//    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        return inflater.inflate(R.layout.main_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        beerName = view.findViewById(R.id.beer_name)
        profileImageView = view.findViewById(R.id.main_profile_image)
        descriptionResponse = view.findViewById(R.id.description_response)
        tagLine = view.findViewById(R.id.tag_line)
        firstBrewed = view.findViewById(R.id.first_brewed)

        profileImageView?.setOnClickListener {
            getBeerResponse()
        }
        getBeerResponse()

    }

    companion object {
        fun newInstance() = MainFragment()
    }

}