package com.example.searchusers

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

class UsersViewModel :ViewModel()
{
    val dataSourceFactory: UserDataSourceFactory by lazy {
        UserDataSourceFactory(viewModelScope)
    }
    val livePagedList = MediatorLiveData<PagedList<Item>>()
    private val config = PagedList.Config.Builder()
        .setPageSize(10)
        .setEnablePlaceholders(false)
        .build()

    fun fetchUsers() {
        livePagedList.addSource(LivePagedListBuilder<Int, Item>(dataSourceFactory, config).build()) {
            livePagedList.value = it
        }
    }

    fun updateSearch(newText: String) {
        dataSourceFactory.searchText = newText
        livePagedList.addSource(LivePagedListBuilder<Int, Item>(dataSourceFactory, config).build()) {
            livePagedList.value = it
        }
    }
}