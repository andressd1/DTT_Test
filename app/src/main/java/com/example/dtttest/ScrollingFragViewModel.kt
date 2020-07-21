package com.example.dtttest

import android.location.Location
import androidx.lifecycle.*
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

/**
 * ViewModel for ScrollingFragment
 */
class ScrollingFragViewModel : ViewModel() {

    // Retrofit Instance using the HousesService class
    val retService = RetrofitInstance.getRetrofitInstance().create(HousesService::class.java)

    // List of houses
    var houseData = ArrayList<HouseItem>()

    // If location has been retrieved
    var gotLocation: Boolean = false

    // User's latitude
    var latitude: Double = 0.0

    // User's longitude
    var longitude: Double = 0.0

    // Value holding live data which is the response to te method getHouses of retService
    // Also manipulates the data
    val responseLiveData: LiveData<Response<House>> = liveData {
        val response = retService.getHouses()
        if (response.body() != null) {
            val listIter = response.body()?.listIterator()
            if (listIter != null) {
                while (listIter.hasNext()) {
                    houseData.add(listIter.next())
                }
                houseData.sortWith(compareBy { it.price })
                if (gotLocation) {
                    setDistance()
                }
            }
        }
        emit(response)
    }

    /**
     * Filters the houses based on a search
     */
    fun filterHouses(search: String): ArrayList<HouseItem> {

        if (search.isEmpty()) {
            return houseData
        } else {
            val filteredList = ArrayList<HouseItem>()
            for (item in houseData) {
                if (item.city.toLowerCase(Locale.ROOT).contains(search) || item.zip.toLowerCase(
                        Locale.ROOT
                    ).contains(search)
                ) {
                    filteredList.add(item)
                }
            }
            return filteredList
        }
    }

    /**
     * Sets user location and updates the distance to the houses if possible
     * @param lo Longitude of user
     * @param la Latitude of user
     */
    fun updateLocation(lo: Double, la: Double) {
        gotLocation = true
        latitude = la
        longitude = lo
        if (houseData.size > 0) {
            setDistance()
        }
    }

    /**
     * Sets the user's distance to the houses
     */
    fun setDistance() {
        for (x in 0 until houseData.size) {
            val f = floatArrayOf(1f)
            Location.distanceBetween(
                latitude,
                longitude,
                houseData[x].latitude.toDouble(),
                houseData[x].longitude.toDouble(),
                f
            )
            houseData[x].distance = (f[0]).roundToInt().toFloat() / 1000
        }
    }


}