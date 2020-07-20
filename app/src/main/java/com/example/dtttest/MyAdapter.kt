package com.example.dtttest

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row.view.*
import kotlin.collections.ArrayList

class MyAdapter( arrayList: ArrayList<HouseItem>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    var filteredList = ArrayList<HouseItem>()
    lateinit var context : Context
    init {
        filteredList = arrayList
    }


     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){


         fun bindItem(item: HouseItem) {
             itemView.houseImageIv.clipToOutline = true
             itemView.priceTv.text = "$"+item.price
             itemView.addressTv.text = item.city + " " + item.zip
             itemView.bedsTv.text = item.bedrooms.toString()
             itemView.bathsTv.text = item.bathrooms.toString()
             itemView.imageNumbTv.text = item.size.toString()
             itemView.distanceTv.text = "${item.distance} km"
         }
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(filteredList[position])

        holder.itemView.setOnClickListener {
            val EXTRA_MESSAGE = "houseItem"
            val item : HouseItem = filteredList[position]
            val intent = Intent(context, HouseProfile::class.java).apply { putExtra(EXTRA_MESSAGE, item) }
            startActivity(context, intent, null)
        }
    }

    fun filteredResults(filteredResults : ArrayList<HouseItem>){
        filteredList = filteredResults
        notifyDataSetChanged()
    }


}