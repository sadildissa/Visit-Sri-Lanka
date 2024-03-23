package com.example.visitsrilankaplases.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.visitsrilankaplases.R

class MainActivity : AppCompatActivity() {

    private lateinit var btnplaces: Button
    private lateinit var btnInsertData: Button
    private lateinit var btnDisplayData: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnplaces = findViewById(R.id.btnplaces)
        btnInsertData = findViewById(R.id.btnInsertData)
        btnDisplayData = findViewById(R.id.btnDisplayData)

        btnInsertData.setOnClickListener {
            val intent = Intent(this, InsertActivity::class.java)
            startActivity(intent)
        }

        btnDisplayData.setOnClickListener {
            val intent = Intent(this, DisplayGuideActivity::class.java)
            startActivity(intent)
        }

        btnplaces.setOnClickListener {
            val intent = Intent(this, PlacesActivity::class.java)
            startActivity(intent)
        }







    }
}