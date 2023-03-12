package com.example.instantgarbagedisposal

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.instantgarbagedisposal.storage.Issue
import com.example.instantgarbagedisposal.storage.Persistance
import com.example.instantgarbagedisposal.utility.ImageViewToBase64

class IssueActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var takePicture : ImageButton
    private val requestCode = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue)

        val garbageType = resources.getStringArray(R.array.garbage_type)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, garbageType)
        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        autocompleteTV.setAdapter(arrayAdapter)

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), requestCode)

        imageView = findViewById(R.id.imageView)
        takePicture = findViewById(R.id.btnTakePicture)


        clickPictureEventListener()

        val postButton: Button = findViewById(R.id.post)

        postButton.setOnClickListener {
            val location = findViewById<EditText>(R.id.editTextTextPostalAddress2)
            val locationValue = location.text.toString()
            val garbageTypeValue = autocompleteTV.text.toString()
            val base64Image = ImageViewToBase64.get(imageView)
            val newIssue: Persistance = Issue(locationValue, garbageTypeValue, base64Image)
            newIssue.save()
            finish()
            Toast.makeText(this, "Posted!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clickPictureEventListener() {
        takePicture.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (cameraIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(cameraIntent, requestCode)
            }
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

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == this.requestCode && resultCode == RESULT_OK){
            val imageBitmap: Bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)

        }
    }


}