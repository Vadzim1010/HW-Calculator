package com.example.hw1calculator.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hw1calculator.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragment = CalculatorFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        }
    }
}