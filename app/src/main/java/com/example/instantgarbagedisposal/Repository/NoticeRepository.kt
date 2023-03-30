package com.example.instantgarbagedisposal.Repository

import androidx.lifecycle.MutableLiveData
import com.example.instantgarbagedisposal.Models.RetrieveData
import com.example.instantgarbagedisposal.utility.orders
import com.google.firebase.database.*

class NoticeRepository {


    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("orders")

    @Volatile private  var INSTANCE: NoticeRepository ? = null

    fun getInstance() : NoticeRepository {
        return INSTANCE ?: synchronized(this){

            val Instance = NoticeRepository()
            INSTANCE = Instance
            Instance

        }
    }

    fun loadIssues(currentNoticeList: MutableLiveData<List<orders>>){


        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val _currentNoticeList : List<orders> = snapshot.children.map { dataSnapshot ->

                        dataSnapshot.getValue(orders::class.java)!!
                    }

                    currentNoticeList.postValue(_currentNoticeList)

                } catch (e : Exception){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}