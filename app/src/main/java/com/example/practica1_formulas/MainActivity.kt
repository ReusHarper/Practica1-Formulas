package com.example.practica1_formulas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // Set the default theme when the app is load and the splash theme when the app is start
        setTheme(R.style.Theme_Practica1Formulas)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}