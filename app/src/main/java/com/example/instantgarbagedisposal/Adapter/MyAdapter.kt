package com.example.instantgarbagedisposal.Adapter

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.instantgarbagedisposal.Models.RetrieveData
import com.example.instantgarbagedisposal.R
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.Base64


class MyAdapter() : RecyclerView.Adapter<MyAdapter.MyViewHolder>(){


    private val userList = ArrayList<RetrieveData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_item,parent,false)
        return MyViewHolder(itemView)

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

        val acceptButton = holder.itemView.findViewById<Button>(R.id.acceptButton)
        val rejectButton = holder.itemView.findViewById<Button>(R.id.rejectButton)
        val acceptedTextView = holder.itemView.findViewById<TextView>(R.id.showTextView)

        acceptButton.setOnClickListener {
            acceptedTextView.visibility = View.VISIBLE
            acceptButton.visibility = View.GONE
            rejectButton.visibility = View.GONE
        }

        rejectButton.setOnClickListener {
            acceptedTextView.text = "REJECTED"
            acceptedTextView.visibility = View.VISIBLE
            acceptedTextView.setTextColor(Color.parseColor("#A91B0D"))
            acceptButton.visibility = View.GONE
            rejectButton.visibility = View.GONE
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
    }

    fun updateUserList(userList: List<RetrieveData>){

        this.userList.clear()
        this.userList.addAll(userList)
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val location : TextView = itemView.findViewById(R.id.cdLocation)
        val garbageImage: ImageView = itemView.findViewById(R.id.cdImageView)
        val garbageType: TextView = itemView.findViewById(R.id.cdGarbageType)
        val showLocation: Button = itemView.findViewById(R.id.showLocationBtn)

    }

}