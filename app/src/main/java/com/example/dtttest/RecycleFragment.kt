package com.example.dtttest

import android.content.Context
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_recycle.*
import kotlin.math.roundToInt

class RecycleFragment(var arrayList: ArrayList<Model>) : Fragment() {

    lateinit var myAdapter: MyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recycle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myAdapter = MyAdapter(arrayList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = myAdapter
    }

    fun searchChange(f : ArrayList<Model>){
        myAdapter.filteredResults(f)
    }

    fun gotUserLocation(longitude : Double, latitude : Double){
        for (x in 0 until arrayList.size) {
            var f = floatArrayOf(1f)
            Location.distanceBetween(
                latitude,
                longitude,
                arrayList[x].latitude,
                arrayList[x].longtitude,
                f
            )
            arrayList[x].distNumb = (f[0]).roundToInt().toFloat() / 1000
            myAdapter.notifyItemChanged(x)
        }
    }

}