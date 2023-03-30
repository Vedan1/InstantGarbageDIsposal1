package com.example.instantgarbagedisposal.fragments

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.instantgarbagedisposal.*
import com.example.instantgarbagedisposal.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.instantgarbagedisposal.databinding.ActivityMainBinding
import com.example.instantgarbagedisposal.databinding.FragmentSettingsBinding
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class SettingsFragment : Fragment() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityLoginBinding

    private lateinit var email : TextInputLayout
    private lateinit var password: TextInputLayout
    private lateinit var loginBtn: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val v = inflater.inflate(R.layout.fragment_post_issue_, container, false)
        val btn = view.findViewById<Button>(R.id.workerbtn)
        val btn1 = view.findViewById<Button>(R.id.supervisorbtn)
      //  val photoUrl = user?.photoUrl


        // Inflate the layout for this fragment
//        binding = FragmentSettingsBinding.inflate(l)
////        setContentView(binding.root)
//
//        auth = Firebase.auth
//        loginBtn = view!!.findViewById(R.id.workerbtn)
//
//        // Configure Google Sign In
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken("1014577373126-ttln5u11s616c0pqdakm2rsk1redvqfo.apps.googleusercontent.com")
//            .requestEmail()
//            .build()
//
//        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
////        val btnSignInGoogle = findViewById<Button>(R.id.btnSignInGoogle)
//
//        binding.btn.setOnClickListener() {
//            signIn()
//        }


//        val IssueActivity = activity.Issue
        //       val issueButton : Button = view?.findViewById(R.id.issueButton)!!



        btn.setOnClickListener {

            val intent = Intent(requireActivity(), Login::class.java)
            startActivity(intent)
        }
        btn1.setOnClickListener {

            val intent = Intent(requireActivity(), Login2::class.java)
            startActivity(intent)
        }



    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, Login.RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->



                if(auth.currentUser!!.email!!.contains("palashwalali25@gmail.com")){
                    Log.d("ERror1", "Error in the login ")
                    val intent: Intent = Intent(requireActivity(),AdminActivity::class.java)
                    startActivity(intent)

                }
                else if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                }
                else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        Toast.makeText(activity,"LogonSucessess",Toast.LENGTH_SHORT).show()
        FirebaseDatabase.getInstance()

        if (user != null) {
            val intent = Intent(activity, AdminActivity::class.java)
            intent.putExtra(EXTRA_NAME, user.displayName)
            startActivity(intent)

        }
    }

    companion object {
        const val RC_SIGN_IN = 1001
        const val EXTRA_NAME = "EXTRA_NAME"
    }
}

