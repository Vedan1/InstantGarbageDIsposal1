package com.example.instantgarbagedisposal.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.instantgarbagedisposal.CreateEventActivity
import com.example.instantgarbagedisposal.OngoingEvents
import com.example.instantgarbagedisposal.R
import com.example.instantgarbagedisposal.UpcomingEventsActivity


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home_, container, false)
        val ongoingEvents: Button = view.findViewById(R.id.ongoing)
        val upcomingEvents: Button = view.findViewById(R.id.upcoming)
        val createEvent: Button = view.findViewById(R.id.creationCleaning)

        ongoingEvents.setOnClickListener {

            val intent: Intent = Intent (requireActivity(),OngoingEvents::class.java)
            startActivity(intent)
        }

        upcomingEvents.setOnClickListener{
            val intent: Intent = Intent (requireActivity(),UpcomingEventsActivity::class.java)
            startActivity(intent)
        }
        createEvent.setOnClickListener {
            val intent: Intent = Intent(requireActivity(),CreateEventActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val text1 = view.findViewById<TextView>(R.id.textv1)!!
//        text1.animate().alpha(1f).setDuration(1000).start()
    }
}
