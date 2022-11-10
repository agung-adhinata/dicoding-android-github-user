package com.nekkiichi.aplikasigithubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.nekkiichi.aplikasigithubuser.databinding.ActivityDetailsBinding
import com.nekkiichi.aplikasigithubuser.datas.UserDetail
import com.nekkiichi.aplikasigithubuser.datas.UserItem
import com.nekkiichi.aplikasigithubuser.datas.models.DetailViewModel

class DetailsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_GITHUB_USER = "extra_github_user"
        const val EXTRA_USER_DETAIL = "extra_user_detail"
    }
    lateinit var binding: ActivityDetailsBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
        viewModel.userDetail.observe(this) {
            setGithubUserView(it)
        }
        //action bar settings
        title = "User Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //intent receiver
        val data = intent.getParcelableExtra<UserItem>(EXTRA_USER_DETAIL) as UserDetail
        viewModel.retrieveUserDetail(data)
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
    private fun setGithubUserView(data: UserDetail) {
        binding.progressBar.visibility = View.GONE
        binding.tvGithubFullname.text = data.name
        binding.tvGithubFollowingCount.text = data.following.toString()
        binding.tvGithubFollowerCount.text = data.followers.toString()
        binding.tvGithubRepoCount.text = data.publicRepos.toString()
        binding.tvGithubUsername.text = data.login
        binding.tvGithubUserLocation.text = data.location
        Glide.with(this).load(data.avatarUrl).circleCrop().into(binding.ivGithubProfile)
    }
    private fun showLoading(b: Boolean) {
        if(b) {
            binding.apply {
                progressBar.visibility = View.VISIBLE
            }
        }else{
            binding.apply {
                progressBar.visibility = View.GONE
            }
        }
    }
}
