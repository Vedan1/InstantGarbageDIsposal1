package com.example.instantgarbagedisposal.Adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.instantgarbagedisposal.R
import com.example.instantgarbagedisposal.Models.RetrieveData
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

        holder.location.text = currentItem.location
        holder.garbageImage.setImageBitmap(bitmap)
        holder.garbageType.text = currentItem.garbage_type
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

    }

}