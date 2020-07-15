package com.example.dtttest

import java.io.Serializable

class Model (val houseImage: Int, val price: String, val address: String, val bedNumb: String,
             val bathNumb: String,  val imagesNumb: String, var distNumb: Float, val latitude: Double, val longtitude: Double) : Serializable {



}