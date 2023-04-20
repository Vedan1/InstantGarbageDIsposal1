package com.example.instantgarbagedisposal.Adapter

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.instantgarbagedisposal.Models.RetrieveData
import com.example.instantgarbagedisposal.R
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.Base64
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task
import com.example.instantgarbagedisposal.storage.WorkerCompltn
import com.example.instantgarbagedisposal.storage.Persistance
import com.example.instantgarbagedisposal.utility.ImageViewToBase64
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Collections.min


class MyAdapter(private val context: Context) : RecyclerView.Adapter<MyAdapter.MyViewHolder>(){


    private val userList = ArrayList<RetrieveData>()
    private val expandedPositions = HashSet<Int>()
    val PREFS_NAME = "my_prefs"
    private val requestCode = 100

    val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_item,parent,false)
        val viewHolder = MyViewHolder(itemView, context, requestCode)
        return viewHolder

    }

    override fun getItemCount(): Int {

        return userList.size
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        val imageBytes = Base64.decodeBase64(currentItem.image)
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        val coordinatesString = currentItem.coordinates
        val latlng = coordinatesString?.split(" ")
        val lat = latlng?.get(0)
        val long = latlng?.get(1)
        holder.location.text = currentItem.location
        holder.garbageImage.setImageBitmap(bitmap)
        holder.garbageType.text = currentItem.garbage_type

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.CAMERA), requestCode)







        if (sharedPref.getBoolean("card_${currentItem.key}", false)){

            holder.garbageImage.visibility = View.GONE
            holder.rejectBtn.visibility = View.GONE
            holder.location.visibility = View.GONE
            holder.acceptBtn.visibility = View.GONE
            holder.showLocation.visibility = View.VISIBLE
            holder.garbageType.visibility = View.GONE
            holder.locationTxt.visibility = View.GONE
            holder.garbageTypeTxt.visibility = View.GONE
            holder.takePhotoBtn.visibility = View.VISIBLE
            holder.submitPhoto.visibility = View.VISIBLE
            holder.submitBtn.visibility = View.VISIBLE


            currentItem.isExpanded = true // mark card as expanded
            expandedPositions.add(position) //
        } else {
            holder.garbageImage.visibility = View.VISIBLE
            holder.rejectBtn.visibility = View.VISIBLE
            holder.location.visibility = View.VISIBLE
            holder.acceptBtn.visibility = View.VISIBLE
            holder.showLocation.visibility = View.VISIBLE
            holder.garbageType.visibility = View.VISIBLE
            holder.locationTxt.visibility = View.VISIBLE
            holder.garbageTypeTxt.visibility = View.VISIBLE
            holder.takePhotoBtn.visibility = View.GONE
            holder.submitPhoto.visibility = View.GONE
            holder.submitBtn.visibility = View.GONE

            currentItem.isExpanded = false // mark card as not expanded
            expandedPositions.remove(position) //

        }


        holder.acceptBtn.setOnClickListener {

            currentItem.isExpanded = true // mark card as expanded
            expandedPositions.add(position) // add position to set of expanded positions
            notifyItemChanged(position)



            val editor = sharedPref.edit()
            editor.putBoolean("card_${currentItem.key}", true)
            editor.apply()


        }

        holder.rejectBtn.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            currentItem.key?.let { key ->
                val ref = database.getReference("Issues").child(key)
                ref.removeValue()
            }

            userList.removeAt(position)
            notifyItemRemoved(position)

        }

        holder.showLocation.setOnClickListener {
            val strUri = "http://maps.google.com/maps?q=loc:$lat,$long"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(strUri))

            intent.setClassName(
                "com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity"
            )

            it.context.startActivity(intent)
        }

        holder.takePhotoBtn.setOnClickListener {


            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (cameraIntent.resolveActivity(context.packageManager) != null) {
                holder.startActivityForResult(cameraIntent, requestCode)


            }
        }

        holder.submitBtn.setOnClickListener {
            val base64img = ImageViewToBase64.get(holder.garbageImage)
            val newWorkerCompltn: Persistance = WorkerCompltn(base64img)
            newWorkerCompltn.save()
//                // Once the image has been saved successfully, delete the item from the adapter
//                userList.removeAt(holder.adapterPosition)
//                notifyItemRemoved(holder.adapterPosition)
//            }.addOnFailureListener {
//                // Handle any errors that occur during the save process
//                Toast.makeText(context, "Failed to upload", Toast.LENGTH_SHORT).show()
//            }
            val database = FirebaseDatabase.getInstance()
            currentItem.key?.let { key ->
                val ref = database.getReference("Issues").child(key)
                ref.removeValue()
            }

            userList.removeAt(position)
            notifyItemRemoved(position)
        }

    }



    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?,holder: MyViewHolder) {
        if (requestCode == this.requestCode && resultCode == AppCompatActivity.RESULT_OK) {
            val imageBitmap: Bitmap = data?.extras?.get("data") as Bitmap
            holder.submitPhoto.setImageBitmap(imageBitmap)
            notifyDataSetChanged()

        }
    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        holder.onActivityResult(requestCode, resultCode, data)
//        for (holder in ntrecyclerView.findViewHolderForAdapterPosition(position) as MyViewHolder) {
//        }
//    }






    fun updateUserList(userList: List<RetrieveData>){

        this.userList.clear()
        this.userList.addAll(userList)
        notifyDataSetChanged()
    }

    @Deprecated("Deprecated in Java")



    class MyViewHolder(itemView : View,val context: Context,val requestCode: Int) : RecyclerView.ViewHolder(itemView) {

        val location : TextView = itemView.findViewById(R.id.cdLocation)
        val garbageImage: ImageView = itemView.findViewById(R.id.cdImageView)
        val garbageType: TextView = itemView.findViewById(R.id.cdGarbageType)
        val showLocation: Button = itemView.findViewById(R.id.showLocationBtn)
        val acceptBtn: Button = itemView.findViewById(R.id.acceptButton)
        val rejectBtn: Button = itemView.findViewById(R.id.rejectButton)
        val submitBtn: Button = itemView.findViewById(R.id.submitBtn)
        val locationTxt: TextView = itemView.findViewById(R.id.locationTxtView)
        val garbageTypeTxt: TextView = itemView.findViewById(R.id.garbageTypeTxtView)
        val takePhotoBtn : Button = itemView.findViewById(R.id.takePhotoBtn)
        val submitPhoto: ImageView = itemView.findViewById(R.id.submitImageView)


        fun startActivityForResult(intent: Intent,requestCode: Int){
            (context as Activity).startActivityForResult(intent,requestCode)
        }







    }


}