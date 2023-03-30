package com.example.instantgarbagedisposal.Repository

import androidx.lifecycle.MutableLiveData
import com.example.instantgarbagedisposal.Models.RetrieveData
import com.google.firebase.database.*

class DataRepository {

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Issues")

    @Volatile private  var INSTANCE: DataRepository ? = null

    fun getInstance() : DataRepository{
        return INSTANCE ?: synchronized(this){

            val Instance = DataRepository()
            INSTANCE = Instance
            Instance

        }
    }

    fun loadIssues(currentList: MutableLiveData<List<RetrieveData>>){


       databaseReference.addValueEventListener(object : ValueEventListener{
           override fun onDataChange(snapshot: DataSnapshot) {
              try {
                  val _currentList : List<RetrieveData> = snapshot.children.map { dataSnapshot ->

                      dataSnapshot.getValue(RetrieveData::class.java)!!
                  }

                  currentList.postValue(_currentList)

              } catch (e : Exception){

              }
           }

           override fun onCancelled(error: DatabaseError) {
               TODO("Not yet implemented")
           }

       })
    }
}