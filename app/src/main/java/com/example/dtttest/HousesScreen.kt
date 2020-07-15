package com.example.dtttest

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_houses_screen.*
import kotlin.math.roundToInt


class HousesScreen : AppCompatActivity(){

    var permissionDenied = false
    var gotLocation : Boolean? = null
    private var REQUEST_CODE_LOCATION_PERMISSION = 1
    var longitude : Double = 0.0
    var latitude : Double = 0.0
    val arrayList = ArrayList<Model>()
    lateinit var myAdapter : MyAdapter

    fun requestPermission(adapter: MyAdapter) {
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
            getCurrentLocation(adapter)
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
                getCurrentLocation(myAdapter)
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_LONG).show()
                permissionDenied = true
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(adapter: MyAdapter) {
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
                    for(x in 0 until arrayList.size){
                        var f =  floatArrayOf(1f)
                        Location.distanceBetween(latitude, longitude, arrayList[x].latitude, arrayList[x].longtitude, f)
                        arrayList[x].distNumb = (f[0]).roundToInt().toFloat()/1000
                        adapter.notifyItemChanged(x)
                    }
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


        for (x in 0 until 4) {
            arrayList.add(
                Model(
                    houseImage = R.drawable.house,
                    price = "9999",
                    address = "Balloon Street",
                    bedNumb = "4",
                    bathNumb = "4",
                    imagesNumb = "44",
                    distNumb = 0f,
                    latitude = 40.0,
                    longtitude = -4.0
                )
            )
            arrayList.add(
                Model(
                    houseImage = R.drawable.house,
                    price = "5000",
                    address = "Calle Abrego",
                    bedNumb = "5",
                    bathNumb = "7",
                    imagesNumb = "17",
                    distNumb = 0f,
                    latitude = 40.0,
                    longtitude = -4.0
                )
            )

            arrayList.add(
                Model(
                    houseImage = R.drawable.house,
                    price = "45000",
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
        arrayList.sortWith( compareBy {it.price})
        
        val myColor = ContextCompat.getColor(this, R.color.Strong)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "DTT REAL ESTATE"
        toolbar.setTitleTextColor(myColor)
        setSupportActionBar(toolbar)

        val obj = object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                myAdapter.filter.filter(newText)
                return false
            }


        }

        houseSearch.setOnQueryTextListener(obj)


        myAdapter = MyAdapter(arrayList, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = myAdapter
        requestPermission(myAdapter)

        bottomNavigation.menu.findItem(R.id.action_home).isChecked = true
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                bottomNavigation.selectedItemId -> {
                    false
                }

                R.id.action_home -> {
                    val intent = Intent(this, HousesScreen::class.java)
                    startActivity(intent)
                    true
                }
                R.id.action_about -> {

                    val intent = Intent(this, About::class.java)
                    startActivity(intent)
                    false

                }
                else -> false
            }
        }

    }
}