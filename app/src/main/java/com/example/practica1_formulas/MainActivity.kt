package com.example.practica1_formulas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // Set the default theme when the app is load & the splash theme when the app starts
        setTheme(R.style.Theme_Practica1Formulas)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}