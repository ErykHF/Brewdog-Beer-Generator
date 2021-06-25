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
import com.erykhf.android.brewdogbeergenerator.GlideImageLoader
import com.erykhf.android.brewdogbeergenerator.ImageLoader
import com.erykhf.android.brewdogbeergenerator.R
import com.erykhf.android.brewdogbeergenerator.api.PunkApiService
import com.erykhf.android.brewdogbeergenerator.model.BeerData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://api.punkapi.com/v2/beers/"

class MainFragment : Fragment() {


    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl((BASE_URL))
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
    }

    private val punkApiService by lazy {
        retrofit.create(PunkApiService::class.java)
    }

    private var beerName: TextView? = view?.findViewById(R.id.beer_name)
    private var profileImageView: ImageView? = view?.findViewById(R.id.main_profile_image)
    private var descriptionResponse: TextView? = view?.findViewById(R.id.description_response)
    private var firstBrewed: TextView? = view?.findViewById(R.id.first_brewed)
    private var tagLine: TextView? = view?.findViewById(R.id.tag_line)


    private val imageLoader: ImageLoader by lazy {
        GlideImageLoader(requireActivity())
    }


    private fun getBeerImageResponse() {
        val call = punkApiService.loadImages()
        call.enqueue(object : Callback<List<BeerData>> {
            override fun onResponse(
                    call: Call<List<BeerData>>,
                    response: Response<List<BeerData>>
            ) {
                if (response.isSuccessful) {

                    Log.d("MainActivity", "onResponse: ${response.body()}")

                    val results = response.body()

                    beerName?.text = results?.firstOrNull()?.name ?: "Unknown"
                    descriptionResponse?.text = results?.firstOrNull()?.description
                            ?: "No Description"
                    firstBrewed?.text = ("First brewed: ${results?.firstOrNull()?.first_brewed}" ) ?: "No Data"
                    tagLine?.text = results?.firstOrNull()?.tagline ?: "No tags"


                    val firstImageUrl = results?.firstOrNull()?.image_url ?: ""
                    if (firstImageUrl.isNotBlank()) {
                        if (profileImageView != null) {
                            imageLoader.loadImage(
                                    firstImageUrl,
                                    profileImageView!!
                            )
                        }
                    } else {
                        Log.d("MainActivity", "Missing image URL")
                    }
                }
            }

            override fun onFailure(call: Call<List<BeerData>>, t: Throwable) {
                Log.e("MainActivity", "Failed to get search results", t)
            }

        })
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.main_fragment, container, false)

        beerName = view.findViewById(R.id.beer_name)
        profileImageView = view.findViewById(R.id.main_profile_image)
        descriptionResponse = view.findViewById(R.id.description_response)
        tagLine = view.findViewById(R.id.tag_line)
        firstBrewed = view.findViewById(R.id.first_brewed)

        profileImageView?.setOnClickListener {
            getBeerImageResponse()
        }
        getBeerImageResponse()


        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }

    companion object {
        fun newInstance() = MainFragment()
    }

}