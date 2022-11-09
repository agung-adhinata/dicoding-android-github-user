package com.nekkiichi.aplikasigithubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nekkiichi.aplikasigithubuser.adapter.ListGithubUserAdapter
import com.nekkiichi.aplikasigithubuser.databinding.ActivityHomeBinding
import com.nekkiichi.aplikasigithubuser.datasGithubUser.GithubUser

class HomeActivity : AppCompatActivity() {
    //setup intent extras

    private lateinit var rvGithubUsers: RecyclerView
    private var list: ArrayList<GithubUser> = arrayListOf()
    lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("CREATION",list.toString())
        showRecycleList()
    }
    //functions
    private fun showRecycleList() {
        rvGithubUsers.layoutManager = LinearLayoutManager(this)
        val listGithubUserAdapter = ListGithubUserAdapter(list)
        rvGithubUsers.adapter = listGithubUserAdapter
        listGithubUserAdapter.setOnItemClickCallback(object : ListGithubUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: GithubUser) {
                showGithubUserDetails(data)
            }
        })
    }
    private fun showGithubUserDetails(data: GithubUser) {
        val newIntent = Intent(this,DetailsActivity::class.java)
        newIntent.putExtra(DetailsActivity.EXTRA_GITHUB_USER,data)
        startActivity(newIntent)
    }
}