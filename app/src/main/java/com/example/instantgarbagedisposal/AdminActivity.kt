package com.example.instantgarbagedisposal

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AdminActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val btn1 = findViewById<Button>(R.id.noticegen)

        btn1.setOnClickListener(){
            val i = Intent(this,msgadmin::class.java)
            startActivity(i)
        }



    }







}