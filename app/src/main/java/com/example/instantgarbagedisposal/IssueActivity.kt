package com.example.instantgarbagedisposal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast

class IssueActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue)

        val garbage_type = resources.getStringArray(R.array.garbage_type)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, garbage_type)
        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        autocompleteTV.setAdapter(arrayAdapter)

        val postButton: Button = findViewById(R.id.post)

        postButton.setOnClickListener {
            finish()
            Toast.makeText(this, "Posted!",Toast.LENGTH_SHORT).show()
        }
    }
}