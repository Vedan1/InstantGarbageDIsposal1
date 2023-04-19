package com.example.instantgarbagedisposal.workerfragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.example.instantgarbagedisposal.Models.UserModel
import com.example.instantgarbagedisposal.databinding.FragmentWorkerProfileBinding
import com.example.instantgarbagedisposal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.Exception


class workerProfile : Fragment() {



    private var binding : FragmentWorkerProfileBinding? = null
    private lateinit var mAuth: FirebaseAuth
//    var nameWorker = binding!!.name

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkerProfileBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mAuth = FirebaseAuth.getInstance()

//        val name: TextView = view.findViewById(R.id.name)
        val currentUser = mAuth.currentUser
        val database = Firebase.database
        val myRef = database.getReference("user")

        try {
            myRef.child(currentUser!!.uid).addValueEventListener(object:
                ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    val data = snapshot.getValue(UserModel::class.java)
                    Glide.with(requireContext()).load(data?.picurl).centerCrop().into(binding!!.photoUrl1)

                    binding!!.name.text = data?.uname
//                    binding!!.email.text = data?.email
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("testModel", "Failed to read value.", error.toException())
                }
            })
        }
        catch (e: Exception) {
            Log.d("testlog", e.toString())
        }



//        currentUser?.displayName
//        currentUser?.phoneNumber
//        currentUser?.email
//        currentUser?.uid



    }


}