package com.example.instantgarbagedisposal.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction
import com.example.instantgarbagedisposal.IssueActivity
import com.example.instantgarbagedisposal.R

class PostIssue_Fragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_post_issue_, container, false)
        val btn = v.findViewById<Button>(R.id.issueButton)
//        val IssueActivity = activity.Issue
        //       val issueButton : Button = view?.findViewById(R.id.issueButton)!!
        btn.setOnClickListener {

            val intent = Intent(requireActivity(), IssueActivity::class.java)
            startActivity(intent)
        }





        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}