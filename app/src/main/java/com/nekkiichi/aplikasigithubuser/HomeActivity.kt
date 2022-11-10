package com.nekkiichi.aplikasigithubuser

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nekkiichi.aplikasigithubuser.adapter.ListGithubUserAdapter
import com.nekkiichi.aplikasigithubuser.databinding.ActivityHomeBinding
import com.nekkiichi.aplikasigithubuser.datas.UserDetail
import com.nekkiichi.aplikasigithubuser.datas.UserItem
import com.nekkiichi.aplikasigithubuser.datas.models.MainViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.isLoading.observe(this) {
            setLoading(it)
            if(it == false) {
                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.userList.observe(this) {
            showRecycleList(it)
        }
        viewModel.errorMsg.observe(this) {
            if(it!= null) {
                binding.tvEmptyOrError.visibility = View.VISIBLE
                binding.tvEmptyOrError.text = it
            }
        }
    }
    //functions
    private fun showRecycleList(userList: List<UserItem>) {
        binding.rvGithubUsers.layoutManager = LinearLayoutManager(this)
        val listGithubUserAdapter = ListGithubUserAdapter(userList)
        binding.rvGithubUsers.adapter = listGithubUserAdapter
        listGithubUserAdapter.setOnItemClickCallback(object : ListGithubUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserDetail) {
                showGithubUserDetails(data)
            }
        })
    }
    private fun showGithubUserDetails(data: UserDetail) {
        val newIntent = Intent(this@HomeActivity,DetailsActivity::class.java)
        newIntent.putExtra(DetailsActivity.EXTRA_USER_DETAIL,data)
        startActivity(newIntent)
    }
    private fun setLoading(b: Boolean) {
        if(b) {
            binding.tvEmptyOrError.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        }else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchUserList(query ?:"")
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
    return false
            }

        } )
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.search-> {
                return true
            }
            else -> return false
        }
    }

    private fun searchSetup() {

    }
}

