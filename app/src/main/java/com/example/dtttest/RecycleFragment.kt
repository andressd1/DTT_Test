package com.example.dtttest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_recycle.*

/**
 * Fragment containing the recycleView of houses
 * @param houseList The list of houses to display
 */
class RecycleFragment(val houseList : ArrayList<HouseItem>) : Fragment() {
    // The adapter for recycleView
    lateinit var myAdapter: MyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recycle, container, false)
    }

    /**
     * After the view is created, sets up the adapter for the recyclerView
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myAdapter = MyAdapter(houseList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = myAdapter
    }

    /**
     * Notifies the adapter of any necessary changes to the filteredList
     */
    fun searchChange(f : ArrayList<HouseItem>) {
        myAdapter.filteredResults(f)
    }

    /**
     * Notifies the adapter that user location has been retrieved and that data was modified
     */
    fun gotUserLocation() {
        myAdapter.notifyDataSetChanged()
    }

    /**
     * Notifies the adapter that the houses have been retrieved and that data was modified
     */
    fun gotHouses() {
        myAdapter.notifyDataSetChanged()
    }
}