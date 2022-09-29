package com.nekkiichi.aplikasigithubuser

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.nekkiichi.aplikasigithubuser.datasGithubUser.GithubUser

class DetailsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_GITHUB_USER = "extra_github_user"
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_activity)

        //action bar settings
        title = "User Detail"

        val githubUser = intent.getParcelableExtra(EXTRA_GITHUB_USER, GithubUser::class.java)
    }
}