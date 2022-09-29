package com.nekkiichi.aplikasigithubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nekkiichi.aplikasigithubuser.datasGithubUser.GithubUser

class DetailsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_GITHUB_USER = "extra_github_user"
    }

    lateinit var mvName: TextView
    lateinit var mvUsername: TextView
    lateinit var mvFollower: TextView
    lateinit var mvFollowing: TextView
    lateinit var mvRepo: TextView
    lateinit var ivAvatar: ImageView
    lateinit var tvLocation: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        //action bar settings
        title = "User Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mvUsername = findViewById(R.id.tv_github_username)
        mvName = findViewById(R.id.tv_github_fullname)
        ivAvatar = findViewById(R.id.iv_github_profile)
        mvFollower = findViewById(R.id.tv_github_follower_count)
        mvFollowing = findViewById(R.id.tv_github_following_count)
        mvRepo = findViewById(R.id.tv_github_repo_count)
        tvLocation = findViewById(R.id.tv_github_user_location)
        val githubUser = intent.getParcelableExtra<GithubUser>(EXTRA_GITHUB_USER)!!
        fillAllSystem(githubUser)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun fillAllSystem(data: GithubUser) {
        mvName.text = data.name
        mvFollowing.text = data.following
        mvFollower.text = data.follower
        mvRepo.text = data.repository
        mvUsername.text = data.username
        tvLocation.text = data.location

        Glide.with(this).load(data.avatar).circleCrop().into(ivAvatar)
    }
}