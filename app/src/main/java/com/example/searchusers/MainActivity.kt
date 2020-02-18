package com.example.searchusers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val userAdapter: UserAdapter by lazy {
        UserAdapter()
    }
    lateinit var viewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.apply {
            adapter = userAdapter
        }
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)

        viewModel.livePagedList.observe(this, Observer {
            userAdapter.submitList(it)
        })

      if (savedInstanceState == null) {
         viewModel.fetchUsers()
      }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        val searchView=menu?.findItem(R.id.app_bar_search)?.actionView as SearchView

        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.also{
                    viewModel.updateSearch(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })
        return true
    }
}
