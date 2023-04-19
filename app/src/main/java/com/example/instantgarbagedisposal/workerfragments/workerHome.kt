package com.example.instantgarbagedisposal.workerfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instantgarbagedisposal.Adapter.MyAdapter
import com.example.instantgarbagedisposal.Models.DataViewModel
import com.example.instantgarbagedisposal.R
import com.example.instantgarbagedisposal.storage.Database
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class workerHome : Fragment() {

    private lateinit var v: View
    private lateinit var viewModel: DataViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var root : FirebaseDatabase
    lateinit var adapter: MyAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_worker_home, container, false)


//        recyclerView = v.findViewById(R.id.recyclerView)
//        recyclerView.setHasFixedSize(true)
//        recyclerView.layoutManager







        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        adapter = MyAdapter(requireContext())
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        viewModel.allIssues.observe(viewLifecycleOwner, Observer {

            adapter.updateUserList(it)
        })




    }





}