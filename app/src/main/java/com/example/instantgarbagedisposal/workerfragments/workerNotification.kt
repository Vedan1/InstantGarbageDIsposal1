package com.example.instantgarbagedisposal.workerfragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instantgarbagedisposal.Adapter.NoticeAdapter
import com.example.instantgarbagedisposal.R
import com.example.instantgarbagedisposal.utility.Orders

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class workerNotification : Fragment() {

    private lateinit var view: View
    private lateinit var ntRecyclerView : RecyclerView
    lateinit var NoticeAdapter: NoticeAdapter
//    val notices = mutableListOf<orders>()
    val databaseReference = FirebaseDatabase.getInstance().getReference("orders")
    private lateinit var noNoticeTextView : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_worker_notification, container, false)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ntRecyclerView = view.findViewById(R.id.ntRecyclerView)
        noNoticeTextView = view.findViewById(R.id.noNoticeTextView)
        NoticeAdapter = NoticeAdapter()

        ntRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            ntRecyclerView.setHasFixedSize(true)
            adapter = NoticeAdapter
        }

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val noticeList = mutableListOf<Orders>()

                for (childSnapshot in snapshot.children) {
//                    val notice = childSnapshot.getValue(Orders::class.java)
//                    notice?.let {
//                        if (it.expiryTime >= System.currentTimeMillis()) {
//                            noticeList.add(it)
//                        }
//                    }
                    val message = childSnapshot.child("message").value as String?
                    val timestamp = childSnapshot.child("timestamp").value?.toString()?.toLong() ?: 0L
                    val expiryTime = childSnapshot.child("expiryTime").value?.toString()?.toLong() ?: 0L

                    if(System.currentTimeMillis() < expiryTime){
                        val order = Orders(message, timestamp, expiryTime)
                        noticeList.add(order)
                    }

                }
                if(noticeList.isEmpty()){
                    ntRecyclerView.visibility = View.GONE
                    noNoticeTextView.visibility = View.VISIBLE

                }

                noticeList.sortByDescending { it.timestamp }
                NoticeAdapter.updateNotices(noticeList)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, "Failed to retrieve notices!", Toast.LENGTH_SHORT).show()
            }
        })
    }






}


