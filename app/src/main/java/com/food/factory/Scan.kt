package com.food.factory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.food.factory.User.BarCodeScanner

class Scan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
    }

    fun newPage(view: View) {

        val intent = Intent(this@Scan, BarCodeScanner::class.java)
        startActivity(intent)

    }
}