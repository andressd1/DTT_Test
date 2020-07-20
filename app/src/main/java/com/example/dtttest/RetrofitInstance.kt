package com.example.dtttest

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object{
        val baseURL = "https://intern.docker-dev.d-tt.nl"
        fun getRetrofitInstance() : Retrofit{
            return Retrofit.Builder().baseUrl(baseURL).addConverterFactory(
                GsonConverterFactory.create(
                GsonBuilder().create())).build()
        }

    }
}