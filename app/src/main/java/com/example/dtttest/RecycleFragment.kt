package com.example.dtttest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_recycle.*

class RecycleFragment(val houseList : ArrayList<HouseItem>) : Fragment() {

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
        myAdapter = MyAdapter(houseList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = myAdapter
    }
    fun searchChange(f : ArrayList<HouseItem>) {
        myAdapter.filteredResults(f)
    }

    fun gotUserLocation() {
        myAdapter.notifyDataSetChanged()
    }
    fun gotHouses() {
        myAdapter.notifyDataSetChanged()
    }
}