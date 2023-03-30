package com.example.instantgarbagedisposal.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instantgarbagedisposal.Repository.DataRepository


class DataViewModel : ViewModel() {


    private val repository : DataRepository
    private val _allIssues = MutableLiveData<List<RetrieveData>>()
    val allIssues : LiveData<List<RetrieveData>> = _allIssues

    init {
        repository = DataRepository().getInstance()
        repository.loadIssues(_allIssues)
    }
}