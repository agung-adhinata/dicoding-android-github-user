package com.nekkiichi.aplikasigithubuser.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nekkiichi.aplikasigithubuser.R
import com.nekkiichi.aplikasigithubuser.data.Result
import com.nekkiichi.aplikasigithubuser.adapter.ListGithubUserAdapter
import com.nekkiichi.aplikasigithubuser.databinding.ActivityHomeBinding
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserDetail
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserItem
import com.nekkiichi.aplikasigithubuser.data.models.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.users.collect {
                        showSearchResult(it)
                    }
                }
            }
        }
    }
    private fun showSearchResult(result: Result<List<UserItem>>) {
        when(result) {
            is Result.loading -> setLoading(true)
            is Result.Error -> {
                setLoading(false)
                showErrMsg()
            }
            is Result.Success -> {
                setLoading(false)
                showRecycleList(result.data)
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
        val newIntent = Intent(this@HomeActivity, DetailsActivity::class.java)
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
    private fun showErrMsg() {
        binding.tvEmptyOrError.visibility = View.VISIBLE
        binding.tvEmptyOrError.text = "Error Occurred"

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
            R.id.search -> {
                return true
            }
            else -> return false
        }
    }

}

