package com.example.searchusers

import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.*

class UserDataSource(private val coroutineScope: CoroutineScope, private val searchText: String?) : PageKeyedDataSource<Int, Item>() {
    private val api = UsersApi()
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Item>
    ) {
        coroutineScope.launch {
            val users = searchText?.let { api.getUsers(it, 1).items } ?: emptyList()
            callback.onResult(users, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        coroutineScope.launch {
            val users = searchText?.let { api.getUsers(it, params.key).items } ?: emptyList()
            callback.onResult(
                users,
                if (users.isEmpty()) null else params.key + 1
            )
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        coroutineScope.launch {
            val users = searchText?.let { api.getUsers(it, params.key).items } ?: emptyList()
            callback.onResult(
                users,
                if (users.isEmpty()) null else params.key - 1
            )
        }
    }
}
