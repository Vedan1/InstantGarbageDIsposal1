package com.example.instantgarbagedisposal.Adapter

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.instantgarbagedisposal.R
import com.example.instantgarbagedisposal.utility.Orders
import com.example.instantgarbagedisposal.utility.feedback
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.Base64
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*
import com.example.instantgarbagedisposal.storage.Persistance
import com.example.instantgarbagedisposal.storage.Feedbacks

class FeedbackAdapter(private val context: Context): RecyclerView.Adapter<FeedbackAdapter.FeedbackAdapterViewHolder>(){

    private val feedback = ArrayList<feedback>()

    inner class FeedbackAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.feedbackImage)
        val submit: Button = itemView.findViewById(R.id.feedbackSubmitButton)
        val editText: EditText = itemView.findViewById(R.id.feedbackEditText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackAdapter.FeedbackAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_user, parent, false)
        return FeedbackAdapterViewHolder(view)
    }

    fun updateFeedback (feedback: List<feedback>){
        this.feedback.clear()
        this.feedback.addAll(feedback)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FeedbackAdapter.FeedbackAdapterViewHolder, position: Int) {
        val currentItem = feedback[position]
        val imageBytes = Base64.decodeBase64(currentItem.image)
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        holder.imageView.setImageBitmap(bitmap)

        holder.submit.setOnClickListener {
            if(holder.editText.text.isNotEmpty()){
                Toast.makeText(context as Activity,"Submitted Feedback",Toast.LENGTH_SHORT).show()
                val database = FirebaseDatabase.getInstance()
                val feedbackText: String  = holder.editText.text.toString()
                val newFeedbacks: Persistance = Feedbacks(feedbackText)
                newFeedbacks.save()

                currentItem.key?.let { key ->
                    val ref = database.getReference("User Feedback").child(key)
                    ref.removeValue()
                }

                feedback.removeAt(position)
                notifyItemRemoved(position)
            }
            else{
                Toast.makeText(context as Activity,"Please fill out the feedback!",Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun getItemCount(): Int {
        return feedback.size
    }
}
