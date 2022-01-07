package com.erykhf.android.brewdogbeergenerator.ui.main.savedbeers

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.erykhf.android.brewdogbeergenerator.databinding.FragmentSavedBeerBinding
import com.erykhf.android.brewdogbeergenerator.model.BeerData
import com.erykhf.android.brewdogbeergenerator.utils.Util
import com.erykhf.android.brewdogbeergenerator.utils.Util.loadImages


class SavedBeerRecyclerViewAdapter(
) : RecyclerView.Adapter<SavedBeerRecyclerViewAdapter.ViewHolder>() {


    var values = mutableListOf<BeerData>()

    fun setImageList(images: List<BeerData>) {
        this.values = images.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FragmentSavedBeerBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                holder.bind(values[position])
            }
        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: FragmentSavedBeerBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(beerData: BeerData) {

            binding.apply {

                val title = title
                val description = desc
                val imageView = image

                val progressDrawable = Util.getProgressDrawable(itemView.context)
                val imageLink = beerData.image_url
                imageView.loadImages(imageLink, progressDrawable)
                title.text = beerData.name
                description.text = beerData.tagline
            }
        }
    }
}