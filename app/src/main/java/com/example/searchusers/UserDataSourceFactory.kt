package com.example.searchusers

import androidx.paging.DataSource
import kotlinx.coroutines.CoroutineScope

class UserDataSourceFactory(private val coroutineScope: CoroutineScope) : DataSource.Factory<Int, Item>() {
    var searchText: String? = null

    override fun create(): DataSource<Int, Item> {
        return UserDataSource(coroutineScope, searchText)
    }
}