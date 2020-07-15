package com.example.dtttest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_house_profile.*


class HouseProfile : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    var longtitude :  Double = 0.0
    var latitude : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_profile)

        val i = intent
        val model: Model = i.getSerializableExtra("houseModel") as Model

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        houseProfileImage.setImageResource(model.houseImage)
        priceProfile.text = "$" + model.price
        bedsTv.text = model.bedNumb
        bathsTv.text = model.bathNumb
        imageNumbTv.text = model.imagesNumb
        distanceTv.text = "${model.distNumb} km"
        longtitude = model.longtitude
        latitude = model.latitude
    }

    fun onClickButton(view: View) {
        finish()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Add a marker in Sydney and move the camera
        val markerLongLat = LatLng(latitude, longtitude)
        val zoom = 10f
        map.addMarker(MarkerOptions().position(markerLongLat).title("Home"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLongLat, zoom))
        map.uiSettings.setAllGesturesEnabled(false)



        map.setOnMapClickListener(object : GoogleMap.OnMapClickListener {
            override fun onMapClick(p0: LatLng?) {
                val gmmIntentUri =
                    Uri.parse("geo:0,0?q=$latitude,$longtitude(Home)")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
        })
    }


}