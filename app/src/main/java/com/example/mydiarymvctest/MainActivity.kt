package com.example.mydiarymvctest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun select(search: String) {
        Toast.makeText(this, search, Toast.LENGTH_SHORT).show()
    }
}