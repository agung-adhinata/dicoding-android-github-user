package com.nekkiichi.aplikasigithubuser.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nekkiichi.aplikasigithubuser.ui.FollowFragment

class SectionDetailPagerAdapter(activity: AppCompatActivity, private val username: String) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowFragment().apply {
                arguments = Bundle().apply {
                    putString(FollowFragment.USERNAME, username)
                    putBoolean(FollowFragment.IS_FOLLOWER, true)
                }
            }
            else -> FollowFragment().apply {
                arguments = Bundle().apply {
                    putString(FollowFragment.USERNAME, username)
                    putBoolean(FollowFragment.IS_FOLLOWER, false)
                }
            }
        }
    }
}
