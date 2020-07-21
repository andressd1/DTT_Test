package com.example.dtttest

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import java.util.*


/**
 * Main activity of the app. Displays a splash screen for the user for 1.5 seconds, then
 * takes the to the HousesScreen to view the houses and the rest of actions
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (supportActionBar != null)
        supportActionBar?.hide()

        setContentView(R.layout.activity_main)
        val intent = Intent(this, HousesScreen::class.java)
        Handler().postDelayed({
            startActivity(intent) }, 1500)
    }
}