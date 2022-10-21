package com.example.practica1_formulas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner

class MainActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {

        // Set the default theme when the app is load & the splash theme when the app starts
        setTheme(R.style.Theme_Practica1Formulas)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}