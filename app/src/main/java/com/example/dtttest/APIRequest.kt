package com.example.dtttest

import java.net.URL

class APIRequest {
    val url = "https://intern.docker-dev.d-tt.nl/api/house"
    val accessKey = "98bww4ezuzfePCYFxJEWyszbUXc7dxRx"
    fun run(){
        val housesJson = URL(url).readText();

    }
}