package com.example.dtttest


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HouseItem(
    @SerializedName("bathrooms")
    val bathrooms: Int,
    @SerializedName("bedrooms")
    val bedrooms: Int,
    @SerializedName("city")
    val city: String,
    @SerializedName("createdDate")
    val createdDate: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("latitude")
    val latitude: Int,
    @SerializedName("longitude")
    val longitude: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("zip")
    val zip: String,
    var distance : Float = 0f
) : Serializable