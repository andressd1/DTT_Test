package com.example.dtttest

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (supportActionBar != null)
        supportActionBar?.hide()

        setContentView(R.layout.activity_main)
        val delay : Long = 2000
        val intent = Intent(this, HousesScreen::class.java)
        Handler().postDelayed({
            startActivity(intent) }, delay)
    }
}