package com.example.instantgarbagedisposal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instantgarbagedisposal.databinding.ActivityMsgadminBinding
import com.example.instantgarbagedisposal.utility.user
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class msgadmin : AppCompatActivity() {

    private lateinit var binding : ActivityMsgadminBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_msgadmin)
        binding = ActivityMsgadminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tsLong = System.currentTimeMillis() / 1000
        val ts = tsLong.toString()

        binding.submit.setOnClickListener {

            val msgorder = binding.textInputEditText.text.toString()


            database = FirebaseDatabase.getInstance().getReference("orders")

            val User = user(msgorder,ts)
            database.child(ts).setValue(User).addOnSuccessListener {

                binding.textInputEditText.text?.clear()
//                binding.lastName.text.clear()
//                binding.age.text.clear()
//                binding.userName.text.clear()

                Toast.makeText(this,"Successfully Saved", Toast.LENGTH_SHORT).show()
                val i = Intent(this,AdminActivity::class.java)
                startActivity(i)

            }.addOnFailureListener{

                Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()


            }


        }

    }
}