package com.example.instantgarbagedisposal

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class IssueActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var takePicture : ImageButton
    private val REQUEST_CODE = 100
    private val REQUEST_GALLERY_CAMERA = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue)

        val garbage_type = resources.getStringArray(R.array.garbage_type)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, garbage_type)
        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        autocompleteTV.setAdapter(arrayAdapter)

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CODE)

        imageView = findViewById(R.id.imageView)
        takePicture = findViewById(R.id.btnTakePicture)


        takePicture.setOnClickListener{
            val cameraIntent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (cameraIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(cameraIntent, REQUEST_CODE)
            }
        }


        val postButton: Button = findViewById(R.id.post)



        postButton.setOnClickListener {
            finish()
            Toast.makeText(this, "Posted!",Toast.LENGTH_SHORT).show()
        }
    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUEST_GALLERY_CAMERA) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                openCamera()
//            } else {
//                Toast.makeText(this@CameraIntentActivity, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            val imageBitmap: Bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)

        }



    }


}