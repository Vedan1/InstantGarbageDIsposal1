package com.example.instantgarbagedisposal.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.instantgarbagedisposal.R
import com.example.instantgarbagedisposal.utility.Orders
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NoticeAdapter() : RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>() {

    private val notices = ArrayList<Orders>()

    inner class NoticeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noticeContentTextView: TextView = itemView.findViewById(R.id.noticeText)
        val noticeTimestampTextView: TextView = itemView.findViewById(R.id.noticeTimestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cardview_notice, parent, false)
        return NoticeViewHolder(view)
    }

    fun updateNotices (notices: List<Orders>){
        this.notices.clear()
        this.notices.addAll(notices)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        val notice = notices[position]

        holder.noticeContentTextView.text = notice.message
        holder.noticeTimestampTextView.text =
            SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(notice.timestamp)
    }

    override fun getItemCount(): Int {
        return notices.size
    }
}