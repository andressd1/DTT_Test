package com.example.dtttest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_scrolling.*
import java.util.*
import kotlin.collections.ArrayList


class ScrollingFragment() : Fragment() {

    var currentFragment = 1
    lateinit var recycleFragment: RecycleFragment
    val viewModel: RecycleFragViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for context fragment
        return inflater.inflate(R.layout.fragment_scrolling, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragment1: Fragment = RecycleFragment(viewModel.houseData)
        recycleFragment = fragment1 as RecycleFragment
        val fragment2 : Fragment = NoResultFragment()
        val childFragmentManager : FragmentManager = childFragmentManager
        childFragmentManager.beginTransaction().add(R.id.search_container, fragment2, "2").hide(fragment2).commit()
        childFragmentManager.beginTransaction().add(R.id.search_container,fragment1, "1").commit()
        viewModel.responseLiveData.observe(viewLifecycleOwner){
            recycleFragment.gotHouses()
        }

        val obj = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText != null) {
                    val filteredList = viewModel.filterHouses(newText)

                    if(filteredList.size < 1){
                        childFragmentManager.beginTransaction().show(fragment2).hide(fragment1).commit()
                        currentFragment = 2
                    }
                    else{
                        if(currentFragment == 2){
                            currentFragment = 1
                            childFragmentManager.beginTransaction().show(fragment1).hide(fragment2).commit()
                        }
                        recycleFragment.searchChange(filteredList)
                    }
                }
                return false
            }
        }
        houseSearch.setOnQueryTextListener(obj)
    }

    fun gotUserLocation(lo: Double, la: Double) {
        viewModel.updateLocation(lo,la)
        recycleFragment.gotUserLocation()
    }
}