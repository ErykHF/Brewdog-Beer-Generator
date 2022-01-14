package com.erykhf.android.brewdogbeergenerator.ui.main.savedbeers

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        holder.bind(beer)
        holder.itemView.isLongClickable = true

        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let {
                    it(beer)
                }
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: FragmentSavedBeerBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnLongClickListener {

        init {
            itemView.setOnLongClickListener(this)
        }

        fun bind(beerData: BeerData) {

            binding.apply {

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
        }

        override fun onLongClick(v: View): Boolean {
            Toast.makeText(v.context, "long click", Toast.LENGTH_SHORT).show()
            // Return true to indicate the click was handled
            val position = adapterPosition
            val beer = values[position]
            Log.d("testing ", "pos$beer")
            onLongClickListener?.let {
                it(beer)
            }

            return true
        }
    }

    private var onItemClickListener: ((BeerData) -> Unit)? = null

    fun setOnItemClickListener(listener: (BeerData) -> Unit) {
        onItemClickListener = listener
    }

    private var onLongClickListener: ((BeerData) -> Unit)? = null


    fun onLongClick(listener: (BeerData) -> Unit) {
        onLongClickListener = listener
    }
}