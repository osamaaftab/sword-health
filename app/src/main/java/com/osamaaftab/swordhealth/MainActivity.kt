package com.osamaaftab.swordhealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.osamaaftab.dog.ui.list.BreedsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, BreedsActivity::class.java)
        startActivity(intent)
        finish()
    }
}