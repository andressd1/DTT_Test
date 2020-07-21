package com.example.dtttest

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_houses_screen.*

/**
 * Class containing the fragments for the house recycle view and about fragment
 * Also checks for location permission from the user
 */
class HousesScreen : AppCompatActivity() {
    // Whether the user has granted location permission or not
    var permissionDenied = false
    // Whether the user's location has been retrieved
    var gotLocation: Boolean? = null
    // Request code for getting location permission
    private var REQUEST_CODE_LOCATION_PERMISSION = 1
    // Variable that will contain the Scrolling fragment of the activity
    lateinit var fragment: ScrollingFragment

    /**
     * Requests location permission from the user if needed or calls getCurrentLocation()
     * @param fragment The ScrollingFragment to which to pass the location
     */
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


    /**
     * Checks the result of the request for user location and calls getCurrentLocation() or notifies the
     * user that permission was denied
     * @param requestCode The request code of the request
     * @param permissions The permissions to check request's results for
     * @param grantResults The results of the requests
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation(fragment)
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_LONG).show()
                permissionDenied = true
            }
        }
    }

    /**
     * Retrieves the users most recent location
     */
    @SuppressLint("MissingPermission")
    fun getCurrentLocation(fragment: ScrollingFragment) {
        val l = LocationRequest()
        l.interval = 10000
        l.fastestInterval = 3000
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->

            if (location != null) {
                gotLocation = true
                fragment.gotUserLocation(location.longitude, location.latitude)
            } else {
                gotLocation = false
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Creates the activity setting up the toolbar, fragments and the bottomNavigation and initiating the request for location
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_houses_screen)

        val myColor = ContextCompat.getColor(this, R.color.Strong)
        toolbar.title = "DTT REAL ESTATE"
        toolbar.setTitleTextColor(myColor)
        setSupportActionBar(toolbar)

        val fragment1: Fragment = ScrollingFragment()
        val fragment2: Fragment = AboutFragment()
        val fm: FragmentManager = supportFragmentManager
        fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit()
        fm.beginTransaction().add(R.id.main_container, fragment1, "1").commit()

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                bottomNavigation.selectedItemId -> {
                    false
                }

                R.id.action_home -> {
                    toolbar.title = "DTT REAL ESTATE"
                    fm.beginTransaction().hide(fragment2).show(fragment1).commit()
                    true
                }
                R.id.action_about -> {
                    toolbar.title = "ABOUT"
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