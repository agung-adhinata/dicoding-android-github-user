package com.nekkiichi.aplikasigithubuser.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nekkiichi.aplikasigithubuser.adapter.ListGithubUserAdapter
import com.nekkiichi.aplikasigithubuser.data.Result
import com.nekkiichi.aplikasigithubuser.data.models.FavouriteViewModel
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserDetail
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserItem
import com.nekkiichi.aplikasigithubuser.databinding.ActivityFavouriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavouriteBinding
    private val favouriteViewModel: FavouriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Favorites"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        favouriteViewModel.favourites.observe(this) {
            if (it != null) {
                Log.d(TAG, "done getting data")
                favouriteViewModel.getUserLists()
            }
        }
        lifecycleScope.launchWhenStarted {
            launch {
                favouriteViewModel.favouritesList.collect {
                    showFavouriteLists(it)
                }
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        favouriteViewModel.getFavouriteUsers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> false
        }
    }

    fun showFavouriteLists(data: Result<List<UserItem>>) {
        when (data) {
            is Result.loading -> showLoading(true)
            is Result.Error -> {
                showLoading(false)
            }
            is Result.Success -> {
                showLoading(false)
                if(data.data.isEmpty()) {
                    binding.tvEmpty.visibility = View.VISIBLE
                    createRecycleView(listOf<UserItem>())
                }else{
                    createRecycleView(data.data)
                }
            }
        }
    }

    //functions
    fun createRecycleView(userList: List<UserItem>) {
        Log.d(TAG, "done, data: \n${userList}")
        binding.rvUserFavourites.layoutManager = LinearLayoutManager(this)
        val listGithubUserAdapter = ListGithubUserAdapter(userList,applicationContext)
        binding.rvUserFavourites.adapter = listGithubUserAdapter
        listGithubUserAdapter.setOnItemClickCallback(object :
            ListGithubUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserDetail) {
                Log.d(TAG, "clicked")
                val newIntent = Intent(this@FavouriteActivity, DetailsActivity::class.java)
                newIntent.putExtra(DetailsActivity.EXTRA_USER_DETAIL, data)
                startActivity(newIntent)
            }
        })
    }

    fun showLoading(boolean: Boolean) {
        if(boolean) {
            binding.tvEmpty.visibility = View.GONE
        }
    }

    companion object {
        val TAG = "FavouriteActivity"
    }
}
