package com.example.instantgarbagedisposal.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
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
import com.google.firebase.database.FirebaseDatabase



class MyAdapter(private val context: Context) : RecyclerView.Adapter<MyAdapter.MyViewHolder>(){


    private val userList = ArrayList<RetrieveData>()
    private val expandedPositions = HashSet<Int>()
    val PREFS_NAME = "my_prefs"

    val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


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







        if (sharedPref.getBoolean("card_${currentItem.key}", false)){

            holder.garbageImage.visibility = View.GONE
            holder.rejectBtn.visibility = View.GONE
            holder.location.visibility = View.GONE
            holder.acceptBtn.visibility = View.GONE
            holder.showLocation.visibility = View.VISIBLE
            holder.garbageType.visibility = View.GONE
            holder.locationTxt.visibility = View.GONE
            holder.garbageTypeTxt.visibility = View.GONE
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
    }

    fun updateUserList(userList: List<RetrieveData>){

        this.userList.clear()
        this.userList.addAll(userList)
        notifyDataSetChanged()
    }

//    fun saveState(outState: Bundle) {
//        outState.putIntegerArrayList("expanded_positions", ArrayList(expandedPositions))
//    }
//
//    // restore expanded positions when activity is created/recreated
//    fun restoreState(savedInstanceState: Bundle?) {
//        if (savedInstanceState != null) {
//            val positions = savedInstanceState.getIntegerArrayList("expanded_positions")
//            positions?.forEach { position ->
//                expandedPositions.add(position)
//                userList[position].isExpanded = true
//            }
//            notifyDataSetChanged()
//        }
//    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val location : TextView = itemView.findViewById(R.id.cdLocation)
        val garbageImage: ImageView = itemView.findViewById(R.id.cdImageView)
        val garbageType: TextView = itemView.findViewById(R.id.cdGarbageType)
        val showLocation: Button = itemView.findViewById(R.id.showLocationBtn)
        val acceptBtn: Button = itemView.findViewById(R.id.acceptButton)
        val rejectBtn: Button = itemView.findViewById(R.id.rejectButton)
        val submitBtn: Button = itemView.findViewById(R.id.submitBtn)
        val locationTxt: TextView = itemView.findViewById(R.id.locationTxtView)
        val garbageTypeTxt: TextView = itemView.findViewById(R.id.garbageTypeTxtView)

    }

}