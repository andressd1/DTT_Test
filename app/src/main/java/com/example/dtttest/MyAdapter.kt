package com.example.dtttest

import android.content.Context
import android.content.Intent
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row.view.*
import java.util.*
import kotlin.collections.ArrayList

class MyAdapter( val arrayList: ArrayList<Model>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    var filteredList = ArrayList<Model>()
    lateinit var context : Context
    init {
        filteredList = arrayList
    }


     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){


         fun bindItem(model: Model) {
             itemView.houseImageIv.setImageResource(model.houseImage)
             itemView.houseImageIv.clipToOutline = true
             itemView.priceTv.text = "$"+model.price
             itemView.addressTv.text = model.address
             itemView.bedsTv.text = model.bedNumb
             itemView.bathsTv.text = model.bathNumb
             itemView.imageNumbTv.text = model.imagesNumb
             itemView.distanceTv.text = "${model.distNumb} km"

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
            val EXTRA_MESSAGE = "houseModel"
            val m : Model = filteredList[position]
            val intent = Intent(context, HouseProfile::class.java).apply { putExtra(EXTRA_MESSAGE, m) }
            startActivity(context, intent, null)
        }
    }

    fun filteredResults(filteredResults : ArrayList<Model>){
        filteredList = filteredResults
        notifyDataSetChanged()
    }


}