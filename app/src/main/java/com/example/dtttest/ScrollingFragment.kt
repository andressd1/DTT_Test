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

/**
 * Fragment of the scrolling component. Contains the RecyclerView fragment and the SearchView
 */
class ScrollingFragment : Fragment() {
    // Number of the current fragment displayed
    var currentFragment = 1

    // Contains the recycleFragment
    lateinit var recycleFragment: RecycleFragment

    // ViewModel of the ScrollingFragment
    val viewModel: ScrollingFragViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for context fragment
        return inflater.inflate(R.layout.fragment_scrolling, container, false)
    }

    /**
     * Once view is created, creates and sets up the child fragments and listens for any changes in the ViewModel data
     * Also sets up the SearchView
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragment1: Fragment = RecycleFragment(viewModel.houseData)
        recycleFragment = fragment1 as RecycleFragment
        val fragment2: Fragment = NoResultFragment()
        val childFragmentManager: FragmentManager = childFragmentManager
        childFragmentManager.beginTransaction().add(R.id.search_container, fragment2, "2")
            .hide(fragment2).commit()
        childFragmentManager.beginTransaction().add(R.id.search_container, fragment1, "1").commit()
        // Once the ViewModel's data is modified, notifies the recycler fragment to update itself
        viewModel.responseLiveData.observe(viewLifecycleOwner) {
            recycleFragment.gotHouses()
        }

        /**
         * Sets up the SearchView's actions
         */
        val obj = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText != null) {
                    val filteredList = viewModel.filterHouses(newText)

                    if (filteredList.size < 1) {
                        childFragmentManager.beginTransaction().show(fragment2).hide(fragment1)
                            .commit()
                        currentFragment = 2
                    } else {
                        if (currentFragment == 2) {
                            currentFragment = 1
                            childFragmentManager.beginTransaction().show(fragment1).hide(fragment2)
                                .commit()
                        }
                        recycleFragment.searchChange(filteredList)
                    }
                }
                return false
            }
        }
        houseSearch.setOnQueryTextListener(obj)
    }

    /**
     * Notifies the viewModel of the user's location and for the recyclerFragment to get updated
     */
    fun gotUserLocation(lo: Double, la: Double) {
        viewModel.updateLocation(lo, la)
        recycleFragment.gotUserLocation()
    }
}