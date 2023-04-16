package com.example.instantgarbagedisposal

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.instantgarbagedisposal.storage.Issue
import com.example.instantgarbagedisposal.storage.Persistance
import com.example.instantgarbagedisposal.utility.ImageViewToBase64
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class IssueActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var takePicture : ImageButton
    private val requestCode = 100
    var locs = 0
    var locationManager: LocationManager? = null
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
//    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), 101
                )
            }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val curloc = fusedLocationProviderClient.lastLocation

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val locshow = findViewById<TextView>(R.id.locshow)


        val getloc = findViewById<Button>(R.id.getloc)


            curloc.addOnSuccessListener{
                    if(it != null){
                       locshow.text = "${it.latitude} ${it.longitude}"
                        //Toast.makeText(this,locs,Toast.LENGTH_SHORT)
                    }

            }



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
            val location = findViewById<TextView>(R.id.editTextTextPostalAddress2)
            val locationValue = location.text.toString()
            val latlong = locshow.text.toString()
            val garbageTypeValue = autocompleteTV.text.toString()
            val base64Image = ImageViewToBase64.get(imageView)
            val newIssue: Persistance = Issue(locationValue,latlong,garbageTypeValue,base64Image)
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

    private fun locationEnabled() {
        val lm = getSystemService(LOCATION_SERVICE) as LocationManager
        var gps_enabled = false
        var network_enabled = false
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (!gps_enabled && !network_enabled) {
            AlertDialog.Builder(this@IssueActivity)
                .setTitle("Enable GPS Service")
                .setMessage("We need your GPS location to show Near Places around you.")
                .setCancelable(false)
                .setPositiveButton(
                    "Enable"
                ) { paramDialogInterface, paramInt -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
//    fun getLocation() {
//        try {
//            locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
//            locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5f, onLocationChanged())
//        } catch (e: SecurityException) {
//            e.printStackTrace()
//        }
//    }

//

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == this.requestCode && resultCode == RESULT_OK){
            val imageBitmap: Bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)

        }
    }
    fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

    fun onProviderEnabled(provider: String?) {}

    fun onProviderDisabled(provider: String?) {}


}