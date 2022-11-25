package com.nekkiichi.aplikasigithubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources.getDrawable
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
        viewModel.isFavourite.observe(this) {

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
            R.id.favourite -> {
                viewModel.toggleFavourite()
                Toast.makeText(this, applicationContext.getText(R.string.favo_clicked), Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        val favouriteToggleMenu = menu?.findItem(R.id.favourite)
        viewModel.isFavourite.observe(this) {
            if(it){
                favouriteToggleMenu?.icon = getDrawable(applicationContext,R.drawable.ic_baseline_favorite_24)
            }
            else {
                favouriteToggleMenu?.icon = getDrawable(applicationContext,R.drawable.ic_baseline_favorite_border_24)
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_menu,menu)

        return super.onCreateOptionsMenu(menu)
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
