package com.example.dtttest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_scrolling.*
import java.util.*
import kotlin.collections.ArrayList


class ScrollingFragment() : Fragment() {

    var currentFragment = 1
    val arrayList = ArrayList<Model>()
    lateinit var recycleFragment: RecycleFragment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for context fragment
        val v = inflater.inflate(R.layout.fragment_scrolling, container, false)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragment1: Fragment = RecycleFragment(arrayList)
        recycleFragment = fragment1 as RecycleFragment
        val fragment2 : Fragment = NoResultFragment()
        val childFragmentManager : FragmentManager = childFragmentManager
        childFragmentManager.beginTransaction().add(R.id.search_container, fragment2, "2").hide(fragment2).commit()
        childFragmentManager.beginTransaction().add(R.id.search_container,fragment1, "1").commit()

        for (x in 0 until 4) {
            arrayList.add(
                Model(
                    houseImage = R.drawable.house,
                    price = 9999,
                    address = "Balloon Street",
                    bedNumb = "4",
                    bathNumb = "4",
                    imagesNumb = "44",
                    distNumb = 0f,
                    latitude = 40.0,
                    longtitude = -4.0
                )
            )
            arrayList.add(
                Model(
                    houseImage = R.drawable.house,
                    price = 5000,
                    address = "Calle Abrego",
                    bedNumb = "5",
                    bathNumb = "7",
                    imagesNumb = "17",
                    distNumb = 0f,
                    latitude = 40.0,
                    longtitude = -4.0
                )
            )

            arrayList.add(
                Model(
                    houseImage = R.drawable.house,
                    price = 45000,
                    address = "Baker Street",
                    bedNumb = "3",
                    bathNumb = "2",
                    imagesNumb = "14",
                    distNumb = 0f,
                    latitude = 40.0,
                    longtitude = -4.0
                )
            )
        }
        arrayList.sortWith(compareBy { it.price })


        val obj = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText != null) {
                    val search = newText.toString().toLowerCase(Locale.ROOT)
                    var filteredList = ArrayList<Model>()

                    if(search.isEmpty()){
                        filteredList = arrayList
                    }
                    else{
                        for(model in arrayList){
                            if(model.address.toLowerCase(Locale.ROOT).contains(search.toLowerCase(
                                    Locale.ROOT))) {
                                filteredList.add(model)
                            }
                        }
                    }
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
        recycleFragment.gotUserLocation(lo,la)
    }

}