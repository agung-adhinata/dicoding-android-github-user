package com.nekkiichi.aplikasigithubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nekkiichi.aplikasigithubuser.adapter.ListGithubUserAdapter
import com.nekkiichi.aplikasigithubuser.databinding.ActivityHomeBinding
import com.nekkiichi.aplikasigithubuser.datas.UserDetail
import com.nekkiichi.aplikasigithubuser.datas.UserItem
import com.nekkiichi.aplikasigithubuser.datas.models.MainViewModel

class HomeActivity : AppCompatActivity() {
    //setup intent extras

    lateinit var binding : ActivityHomeBinding
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
    }
    //functions
    private fun showRecycleList(userList: List<UserItem>) {
        binding.rvGithubUsers.layoutManager = LinearLayoutManager(this)
        val listGithubUserAdapter = ListGithubUserAdapter(userList)
        binding.rvGithubUsers.adapter = listGithubUserAdapter
        listGithubUserAdapter.setOnItemClickCallback(object : ListGithubUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserItem) {
                showGithubUserDetails(data)
            }
        })
    }
    private fun showGithubUserDetails(data: UserItem) {
        val newIntent = Intent(this@HomeActivity,DetailsActivity::class.java)
        newIntent.putExtra(DetailsActivity.EXTRA_USER_DETAIL,data)
        startActivity(newIntent)
    }
    private fun setLoading(b: Boolean) {
        if(b) {
            binding.progressBar.visibility = View.VISIBLE
        }else {
            binding.progressBar.visibility = View.GONE
        }
    }
}

