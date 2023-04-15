package com.example.instantgarbagedisposal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instantgarbagedisposal.databinding.ActivityMsgadminBinding
import com.example.instantgarbagedisposal.utility.Orders
import com.example.instantgarbagedisposal.utility.user
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class msgadmin : AppCompatActivity() {

    private lateinit var binding : ActivityMsgadminBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_msgadmin)
        database = FirebaseDatabase.getInstance().getReference("orders")

        val button: Button = findViewById(R.id.submit)
        val noticeInputEditText: EditText = findViewById(R.id.noticeInputEditText)

        button.setOnClickListener {
            val message = noticeInputEditText.text.toString()

            if (message.isNotEmpty()) {
                val timestamp = System.currentTimeMillis()
                val expiryTime = timestamp + (24 * 60 * 60 * 1000) // Expiry time after 24 hours

                val notice = Orders(message, timestamp, expiryTime)

                database.push().setValue(notice).addOnSuccessListener {
                    Toast.makeText(this, "Notice saved successfully!", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed to save notice!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter a notice!", Toast.LENGTH_SHORT).show()
            }

            val intent= Intent(this,AdminActivity::class.java)
            startActivity(intent)
        }

    }
}