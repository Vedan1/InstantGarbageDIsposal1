package com.example.instantgarbagedisposal.workerfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instantgarbagedisposal.Adapter.MyAdapter
import com.example.instantgarbagedisposal.Adapter.MyNotificationAdapter
import com.example.instantgarbagedisposal.Models.DataViewModel
import com.example.instantgarbagedisposal.Models.NoticeViewModel
import com.example.instantgarbagedisposal.R


class workerNotification : Fragment() {

    private lateinit var view: View
    private lateinit var viewModel: NoticeViewModel
    private lateinit var ntRecyclerView : RecyclerView
    lateinit var adapter: MyNotificationAdapter


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
        ntRecyclerView.layoutManager = LinearLayoutManager(context)
        ntRecyclerView.setHasFixedSize(true)
        adapter = MyNotificationAdapter()
        ntRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(NoticeViewModel::class.java)
        viewModel.allNotices.observe(viewLifecycleOwner, Observer {

            adapter.updateUserList(it)
        })




    }


}