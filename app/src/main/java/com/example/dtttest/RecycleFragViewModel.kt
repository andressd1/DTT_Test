package com.example.dtttest

import android.location.Location
import androidx.lifecycle.*
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class RecycleFragViewModel : ViewModel() {

    val retService = RetrofitInstance.getRetrofitInstance().create(HousesService::class.java)
    var houseData = ArrayList<HouseItem>()
    var gotLocation: Boolean = false
    var latitude: Double = 0.0
    var longitude: Double = 0.0

    val responseLiveData: LiveData<Response<House>> = liveData {
        val response = retService.getHouses()
        if (response.body() != null) {
            val listIter = response.body()?.listIterator()
            if (listIter != null) {
                while (listIter.hasNext()) {
                    houseData.add(listIter.next())
                }
                houseData.sortWith(compareBy { it.price })
                if(gotLocation){
                    setDistance()
                }
            }
        }
        emit(response)
    }

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

    fun updateLocation(lo: Double, la: Double) {
        gotLocation = true
        latitude = la
        longitude = lo
        if(houseData.size>0){
            setDistance()
        }
    }

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