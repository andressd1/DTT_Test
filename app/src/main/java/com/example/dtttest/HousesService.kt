package com.example.dtttest

import retrofit2.http.GET
import retrofit2.http.Headers


interface HousesService {
    @Headers("Access-Key:98bww4ezuzfePCYFxJEWyszbUXc7dxRx")
    @GET("/api/house")
    suspend fun getHouses() : retrofit2.Response<House>

    }