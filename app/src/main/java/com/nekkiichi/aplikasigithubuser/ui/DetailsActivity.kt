package com.nekkiichi.aplikasigithubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.nekkiichi.aplikasigithubuser.R
import com.nekkiichi.aplikasigithubuser.adapter.SectionDetailPagerAdapter
import com.nekkiichi.aplikasigithubuser.databinding.ActivityDetailsBinding
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserDetail
import com.nekkiichi.aplikasigithubuser.data.models.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_GITHUB_USER = "extra_github_user"
        const val EXTRA_USER_DETAIL = "extra_user_detail"
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
    lateinit var binding: ActivityDetailsBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.userDetail.observe(this) {
            setGithubUserView(it)
        }
        //action bar settings
        title = "User Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //intent receiver
        val data = intent.getParcelableExtra<UserDetail>(EXTRA_USER_DETAIL) as UserDetail
        viewModel.retrieveUserDetail(data)

        //setup viewpager
        val sectionDetailPagerAdapter = SectionDetailPagerAdapter(this,data.login.toString())
        binding.vpFollow.adapter = sectionDetailPagerAdapter

        TabLayoutMediator(binding.tabLayout,binding.vpFollow) { tab, pos-> tab.text = resources.getString(
            TAB_TITLES[pos])
        }.attach()
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
        binding.tvGithubFullname.text = data.name
        binding.tvGithubFollowingCount.text = data.following.toString()
        binding.tvGithubFollowerCount.text = data.followers.toString()
        binding.tvGithubRepoCount.text = data.publicRepos.toString()
        binding.tvGithubUsername.text = data.login
        binding.tvGithubUserLocation.text = data.location ?: "no location"
        Glide.with(this).load(data.avatarUrl).circleCrop().into(binding.ivGithubProfile)
    }

}
