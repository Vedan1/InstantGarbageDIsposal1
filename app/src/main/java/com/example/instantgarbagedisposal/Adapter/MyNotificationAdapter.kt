package com.example.instantgarbagedisposal.Adapter

import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.instantgarbagedisposal.Models.RetrieveData
import com.example.instantgarbagedisposal.R
import com.example.instantgarbagedisposal.utility.orders
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.Base64

class MyNotificationAdapter() : RecyclerView.Adapter<MyNotificationAdapter.MyViewHolder>() {

    private val userList = ArrayList<orders>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_item,parent,false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {

        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]



        holder.noticeText.text = currentItem.message

    }

    fun updateUserList(userList: List<orders>){

        this.userList.clear()
        this.userList.addAll(userList)
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val noticeText: TextView = itemView.findViewById(R.id.noticeText)

    }
}