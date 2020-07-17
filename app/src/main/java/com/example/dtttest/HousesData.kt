package com.example.dtttest

import android.location.Location
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class HousesData {

    var housesList = ArrayList<Model>()
    init{
        for (x in 0 until 4) {
            housesList.add(
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
            housesList.add(
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

            housesList.add(
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
        housesList.sortWith(compareBy { it.price })
    }

    fun filterHouses(search : String): ArrayList<Model> {
        var filteredList = ArrayList<Model>()

        if(search.isEmpty()){
            filteredList = housesList
        }
        else{
            for(model in housesList){
                if(model.address.toLowerCase(Locale.ROOT).contains(search.toLowerCase(
                        Locale.ROOT))) {
                    filteredList.add(model)
                }
            }
        }
        return filteredList
    }

    fun updateLocation(longitude : Double, latitude : Double){
        for (x in 0 until housesList.size) {
            var f = floatArrayOf(1f)
            Location.distanceBetween(
                latitude,
                longitude,
                housesList[x].latitude,
                housesList[x].longtitude,
                f
            )
            housesList[x].distNumb = (f[0]).roundToInt().toFloat() / 1000
        }
    }


}