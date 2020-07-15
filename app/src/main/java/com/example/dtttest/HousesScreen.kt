package com.example.dtttest

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_houses_screen.*


class HousesScreen : AppCompatActivity(){

    var permissionDenied = false
    var gotLocation : Boolean? = null
    private var REQUEST_CODE_LOCATION_PERMISSION = 1
    var longitude : Double = 0.0
    var latitude : Double = 0.0
    lateinit var fragment : ScrollingFragment

     fun requestPermission(fragment: ScrollingFragment) {
        if (ContextCompat.checkSelfPermission(
                this@HousesScreen,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this@HousesScreen,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        } else {
            getCurrentLocation(fragment)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.size > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation(fragment)
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_LONG).show()
                permissionDenied = true
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(fragment: ScrollingFragment) {
        val l: LocationRequest = LocationRequest()
        l.interval = 10000
        l.fastestInterval = 3000
        val fusedLocationCLient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationCLient.lastLocation.addOnSuccessListener { location: Location? ->

                if (location != null) {
                    Log.d("here", "banananana")
                    gotLocation = true
                    longitude = location.longitude
                    latitude = location.latitude
                    fragment.gotUserLocation(longitude, latitude)
                }
                else{
                    gotLocation = false
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
                }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_houses_screen)

        val myColor = ContextCompat.getColor(this, R.color.Strong)
        toolbar.title = "DTT REAL ESTATE"
        toolbar.setTitleTextColor(myColor)
        setSupportActionBar(toolbar)

        val fragment1: Fragment = ScrollingFragment(this)
        val fragment2 : Fragment = AboutFragment()
        val fm: FragmentManager = supportFragmentManager
        fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit()
        fm.beginTransaction().add(R.id.main_container,fragment1, "1").commit();

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                bottomNavigation.selectedItemId -> {
                    false
                }

                R.id.action_home -> {
                    toolbar.setTitle("DTT REAL ESTATE")
                    fm.beginTransaction().hide(fragment2).show(fragment1).commit()
                    true
                }
                R.id.action_about -> {
                    toolbar.setTitle("ABOUT")
                    fm.beginTransaction().hide(fragment1).show(fragment2).commit()
                    true

                }
                else -> false
            }
        }
        fragment = fragment1 as ScrollingFragment
        requestPermission(fragment)
    }
}