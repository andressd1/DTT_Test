package com.example.dtttest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.activity_houses_screen.*

class About : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val myColor = ContextCompat.getColor(this, R.color.Strong)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "ABOUT"
        toolbar.setTitleTextColor(myColor)
        setSupportActionBar(toolbar)

        bottomNavigation2.menu.findItem(R.id.action_about).isChecked = true
        bottomNavigation2.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                bottomNavigation2.selectedItemId -> {
                    false
                }

                R.id.action_home -> {
                    finish()
                    true
                }
                R.id.action_about -> {
                    val intent = Intent(this, About::class.java)
                    startActivity(intent)
                    true

                }
                else -> false
            }

        }

    }
}