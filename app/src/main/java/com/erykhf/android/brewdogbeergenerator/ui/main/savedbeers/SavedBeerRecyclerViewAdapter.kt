package com.erykhf.android.brewdogbeergenerator.ui.main.savedbeers

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.erykhf.android.brewdogbeergenerator.databinding.FragmentSavedBeerBinding
import com.erykhf.android.brewdogbeergenerator.model.BeerData
import com.erykhf.android.brewdogbeergenerator.utils.Util
import com.erykhf.android.brewdogbeergenerator.utils.Util.loadImages


class SavedBeerRecyclerViewAdapter(
) : RecyclerView.Adapter<SavedBeerRecyclerViewAdapter.ViewHolder>() {


    private var values = arrayListOf<BeerData>()

    fun setBeerList(images: List<BeerData>) {
        values.clear()
        values.addAll(images)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FragmentSavedBeerBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val beer = values[position]
        with(holder) {
            itemView.tag = beer
            bind(createOnClickListener(binding, beer.image_url, beer), beer)
        }

//        holder.apply {
//            bind(createOnClickListener(binding, beer.image_url, beer), beer)
//        }

    }


    private fun createOnClickListener(
        binding: FragmentSavedBeerBinding,
        imageId: String?,
        beerData: BeerData
    ): View.OnClickListener {
        return View.OnClickListener {
            val directions =
                SavedBeerFragmentDirections.actionSavedBeerFragmentToBeerView(beerData, imageId)
            val extras = FragmentNavigatorExtras(
                binding.title to "title_$imageId",
                binding.desc to "duration_$imageId",
                binding.image to "thumbnail_$imageId"
            )
            it.findNavController().navigate(directions, extras)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: FragmentSavedBeerBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnLongClickListener {

        init {
            itemView.setOnLongClickListener(this)
        }


        fun bind(listener: View.OnClickListener, beerData: BeerData) {

                ViewCompat.setTransitionName(binding.title, "title_${beerData.id}")
                ViewCompat.setTransitionName(binding.desc, "duration_${beerData.id}")
                ViewCompat.setTransitionName(binding.image, "thumbnail_${beerData.id}")
                with(binding) {
                    val progressDrawable = Util.getProgressDrawable(itemView.context)
                    val imageLink = beerData.image_url
                    val noImagePlaceHolder =
                        "https://www.allianceplast.com/wp-content/uploads/2017/11/no-image.png"

                    if (beerData.image_url.isNullOrBlank()) {
                        image.loadImages(noImagePlaceHolder, progressDrawable)
                    } else {
                        image.loadImages(imageLink, progressDrawable)
                    }
                    title.text = beerData.name
                    desc.text = beerData.tagline
                }
                binding.root.setOnClickListener(listener)

        }

        override fun onLongClick(v: View): Boolean {
            val position = absoluteAdapterPosition
            val beer = values[position]
            Log.d("testing ", "pos$beer")
            onLongClickListener?.let {
                it(beer)
            }

            return true
        }
    }

    private var onLongClickListener: ((BeerData) -> Unit)? = null


    fun onLongClick(listener: (BeerData) -> Unit) {
        onLongClickListener = listener
    }
}