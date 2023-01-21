package com.example.instantgarbagedisposal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val issueButton : Button = findViewById(R.id.issueButton)

        issueButton.setOnClickListener {
            val intent: Intent = Intent(
                this,
                IssueActivity::class.java
            )

            startActivity(intent)
        }


    }
}