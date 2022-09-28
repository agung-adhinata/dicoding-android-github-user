package com.nekkiichi.aplikasigithubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nekkiichi.aplikasigithubuser.datas.ListGithubUserAdapter
import com.nekkiichi.aplikasigithubuser.datasGithubUser.GithubUser
import com.nekkiichi.aplikasigithubuser.utils.getGithubLists

class HomeActivity : AppCompatActivity() {
    private lateinit var rvGithubUsers: RecyclerView
    private var list: ArrayList<GithubUser> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        rvGithubUsers = findViewById(R.id.rv_github_users)
        Log.d("CREATION",list.toString())
        list.addAll(listGithubUser)
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
            val gihubUser = GithubUser(dataUsername[i], dataName[i],dataAvatar.getResourceId(i,-1), dataCompany[i],dataLocation[i],dataRepo[i],dataFollower[i], dataFollowing[i])
            listGithubUserItem.add(gihubUser)
        }
        return  listGithubUserItem
    }


    //functions
    private fun showRecycleList() {
        rvGithubUsers.layoutManager = LinearLayoutManager(this)
        val listGithubUserAdapter = ListGithubUserAdapter(list)
        rvGithubUsers.adapter = listGithubUserAdapter
    }
}