package com.nekkiichi.aplikasigithubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nekkiichi.aplikasigithubuser.adapter.ListGithubUserAdapter
import com.nekkiichi.aplikasigithubuser.datasGithubUser.GithubUser

class HomeActivity : AppCompatActivity() {
    //setup intent extras

    private lateinit var rvGithubUsers: RecyclerView
    private var list: ArrayList<GithubUser> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        rvGithubUsers = findViewById(R.id.rv_github_users)
        list.addAll(listGithubUser)

        Log.d("CREATION",list.toString())
        showRecycleList()
    }

    private val listGithubUser :ArrayList<GithubUser>
    get() {
        val dataUsername = resources.getStringArray(R.array.username)
        val dataName = resources.getStringArray(R.array.name)
        val dataAvatar= resources.obtainTypedArray(R.array.avatar)
        val dataCompany = resources.getStringArray(R.array.company)
        val dataRepo = resources.getStringArray(R.array.repository)
        val dataFollower = resources.getStringArray(R.array.followers)
        val dataFollowing = resources.getStringArray(R.array.following)
        val dataLocation = resources.getStringArray(R.array.location)
        val listGithubUserItem = ArrayList<GithubUser>()
        for(i in dataUsername.indices) {
            val gihubUser = GithubUser("@"+dataUsername[i], dataName[i],dataAvatar.getResourceId(i,-1), dataCompany[i],dataLocation[i],dataRepo[i],dataFollower[i], dataFollowing[i])
            listGithubUserItem.add(gihubUser)
        }
        dataAvatar.recycle()
        return  listGithubUserItem
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