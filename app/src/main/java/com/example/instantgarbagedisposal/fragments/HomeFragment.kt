package com.example.instantgarbagedisposal.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instantgarbagedisposal.Adapter.FeedbackAdapter
import com.example.instantgarbagedisposal.Adapter.NoticeAdapter
import com.example.instantgarbagedisposal.R
import com.example.instantgarbagedisposal.utility.Orders
import com.example.instantgarbagedisposal.utility.feedback
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
//import kotlinx.coroutines.NonCancellable.message


class HomeFragment : Fragment() {


    private lateinit var view: View
    private lateinit var userRecyclerView : RecyclerView
    lateinit var FeedbackAdapter: FeedbackAdapter
    private lateinit var noWorkCompleteTextView: TextView
    val databaseReference = FirebaseDatabase.getInstance().getReference("User Feedback")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home_, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val text1 = view.findViewById<TextView>(R.id.textv1)!!
//        text1.animate().alpha(1f).setDuration(1000).start()

        userRecyclerView = view.findViewById(R.id.userRecyclerView)
        FeedbackAdapter = FeedbackAdapter(requireContext())
        userRecyclerView.apply {

            layoutManager = LinearLayoutManager(activity)
            userRecyclerView.setHasFixedSize(true)
            adapter = FeedbackAdapter
        }

        noWorkCompleteTextView = view.findViewById(R.id.noCompletedWorkTextView)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val feedbackList = mutableListOf<feedback>()


                for (childSnapshot in snapshot.children) {
                    val feedback = childSnapshot.getValue(feedback::class.java)
                    feedback?.let {
                        feedback.key = childSnapshot.key
                        feedback
                        feedbackList.add(it)

                    }
                }
                if(feedbackList.isEmpty()){
                    userRecyclerView.visibility = View.GONE
                    noWorkCompleteTextView.visibility = View.VISIBLE
                }


                FeedbackAdapter.updateFeedback(feedbackList)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, "Failed to retrieve completion of workers!", Toast.LENGTH_SHORT).show()
            }
        })
    }




}

