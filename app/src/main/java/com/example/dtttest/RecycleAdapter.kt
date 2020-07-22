package com.example.dtttest

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row.view.*
import kotlin.collections.ArrayList

/**
 * Adapter for the Recycler View of houses
 */
class RecycleAdapter(arrayList: ArrayList<HouseItem>) :
    RecyclerView.Adapter<RecycleAdapter.ViewHolder>() {

    // List of houses that have been filtered
    var filteredList = ArrayList<HouseItem>()
    // Context of the adapter
    lateinit var context : Context

    init {
        filteredList = arrayList
    }

    /**
     * Inner class for a Viewholder assigning data to the different UI components of the row
     */
     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        // Base URL to retrieve house images from
         val base_url = "https://intern.docker-dev.d-tt.nl"

        /**
         * Binds the data from the HouseItem to the UI components of the View of the ViewHolder
         * @param item HouseItem with data to bind to UI components
         */
         fun bindItem(item: HouseItem, context: Context) {
             val priceString = context.getString(R.string.price_string, item.price)
             val distanceString = context.getString(R.string.distance_string, item.distance)
             val addressString = context.getString(R.string.address_string, item.city, item.zip)
             loadImage(itemView.houseImageIv, item.image)

             itemView.houseImageIv.clipToOutline = true
             itemView.priceTv.text = priceString
             itemView.addressTv.text = addressString
             itemView.bedsTv.text = item.bedrooms.toString()
             itemView.bathsTv.text = item.bathrooms.toString()
             itemView.imageNumbTv.text = item.size.toString()
             itemView.distanceTv.text = distanceString
         }

        /**
         * Loads an image with the url into the ImageView using Glide
         * @param imageView ImageView to load image into
         * @param url End of url for the image
         */
         fun loadImage(imageView: ImageView, url : String){
             Glide.with(itemView).load(base_url+url).into(imageView)
         }
     }

    /**
     * Creates and returns a ViewHolder with the layout of row
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(v)
    }

    /**
     * Returns the size of filteredList
     */
    override fun getItemCount(): Int {
        return filteredList.size
    }

    /**
     * Calls the bindItem() method on a ViewHolder with a particular HouseItem from the filteredList
     * Also sets onClickListener on the ViewHolder to start the HouseProfile activity with that
     * ViewHolder's house's data
     * @param holder ViewHolder to bind HouseItem data to
     * @param position Index in filteredList to bind to ViewHolder
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(filteredList[position], context)

        holder.itemView.setOnClickListener {
            val EXTRA_MESSAGE = "houseItem"
            val item : HouseItem = filteredList[position]
            val intent = Intent(context, HouseProfile::class.java).apply { putExtra(EXTRA_MESSAGE, item) }
            startActivity(context, intent, null)
        }
    }

    /**
     * Sets the filteredList to a new arrayList and notifies that the data has changed
     * @param filteredResults New arrayList to display
     */
    fun filteredResults(filteredResults : ArrayList<HouseItem>){
        filteredList = filteredResults
        notifyDataSetChanged()
    }
}