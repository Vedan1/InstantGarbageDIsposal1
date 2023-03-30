package com.example.instantgarbagedisposal.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instantgarbagedisposal.Repository.DataRepository
import com.example.instantgarbagedisposal.Repository.NoticeRepository
import com.example.instantgarbagedisposal.utility.orders

class NoticeViewModel: ViewModel() {

    private val repository : NoticeRepository
    private val _allNotices = MutableLiveData<List<orders>>()
    val allNotices : LiveData<List<orders>> = _allNotices

    init {
        repository = NoticeRepository().getInstance()
        repository.loadIssues(_allNotices)
    }
}