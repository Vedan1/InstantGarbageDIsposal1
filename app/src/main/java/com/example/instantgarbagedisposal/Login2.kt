package com.example.instantgarbagedisposal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.instantgarbagedisposal.databinding.ActivityLoginBinding
import com.example.instantgarbagedisposal.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class Login2 : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityLoginBinding

    private lateinit var email : TextInputLayout
    private lateinit var password: TextInputLayout
    private lateinit var loginBtn: Button

    private lateinit var dummyButton: Button

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, Login.RC_SIGN_IN)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        loginBtn = findViewById(R.id.LoginButton)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1014577373126-ttln5u11s616c0pqdakm2rsk1redvqfo.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
//        val btnSignInGoogle = findViewById<Button>(R.id.btnSignInGoogle)

        binding.btnSignInGoogle.setOnClickListener() {
            signIn()
        }

    //    dummyButton = findViewById(R.id.dummyButton)
       /* dummyButton.setOnClickListener {
            val intent: Intent = Intent(this,WorkerActivity::class.java)
            startActivity(intent)
            finish()
        }*/

        loginBtn.setOnClickListener {
            email = findViewById(R.id.Email)
            password = findViewById(R.id.Password)
            signInUsingEmailAndPass(email.editText.toString(), password.editText.toString())
        }
    }

    private fun signInUsingEmailAndPass(email: String, password: String) {
        // Authenticate user with email and password
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User is signed in, check if user is admin
                    val user = FirebaseAuth.getInstance().currentUser
                    if (user != null) {
                        val userRef = FirebaseFirestore.getInstance().collection("Users").document(user.uid)
                        userRef.get().addOnSuccessListener { document ->
                            if (document.exists()) {
                                val userType = document.getString("userType")
                                if (userType == "admin") {
                                    // User is admin, navigate to admin activity
                                    val intent = Intent(this, AdminActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    // User is not admin, show error message
                                    Toast.makeText(this, "You do not have admin privileges", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                // User document does not exist, show error message
                                Toast.makeText(this, "User document not found", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    // Authentication failed, show error message
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(ContentValues.TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(ContentValues.TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->



                if(auth.currentUser!!.email!!.contains("palashwalali25@gmail.com")){
                    Log.d("ERror1", "Error in the login ")
                    val intent: Intent = Intent(this,AdminActivity::class.java)
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
        Toast.makeText(applicationContext,"Logon Sucessful",Toast.LENGTH_SHORT).show()
        FirebaseDatabase.getInstance()

        if (user != null) {
            val intent = Intent(applicationContext, AdminActivity::class.java)
            intent.putExtra(EXTRA_NAME, user.displayName)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        const val RC_SIGN_IN = 1001
        const val EXTRA_NAME = "EXTRA_NAME"
    }
}
