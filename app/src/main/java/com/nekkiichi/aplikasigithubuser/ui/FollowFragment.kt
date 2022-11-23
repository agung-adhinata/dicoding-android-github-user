package com.nekkiichi.aplikasigithubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nekkiichi.aplikasigithubuser.adapter.ListGithubUserAdapter
import com.nekkiichi.aplikasigithubuser.databinding.FragmentFollowBinding
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserDetail
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserItem
import com.nekkiichi.aplikasigithubuser.data.models.DetailViewModel

class FollowFragment : Fragment() {
    private var username: String? = null
    private var isFollower = false
    private val viewModel: DetailViewModel by activityViewModels()
    private lateinit var _binding: FragmentFollowBinding
    val binding get() = _binding

    companion object {
        val USERNAME = "username"
        val IS_FOLLOWER = "is_follower"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(USERNAME)
            isFollower = it.getBoolean(IS_FOLLOWER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater,container,false)

        username?.let {
            if(isFollower)
                viewModel.getUserFollower(it)
            else
                viewModel.getUserFollowing(it)
        }
        if(isFollower) {
            viewModel.userFollower.observe(this.viewLifecycleOwner) {
                    createRecycleView(it)
            }
        }else {
            viewModel.userFollowing.observe(this.viewLifecycleOwner) {
                    createRecycleView(it)
            }
        }
        viewModel.isLoadingFollow.observe(this.viewLifecycleOwner) {
            showLoading(it)
        }
        // Inflate the layout for this fragment
        return binding.root
    }
    private fun createRecycleView(data: List<UserItem>) {
        val layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL,false)
        val listGithubUserAdapter = ListGithubUserAdapter(data)
        binding.rvListUserFollow.layoutManager = layoutManager
        binding.rvListUserFollow.adapter = listGithubUserAdapter
        listGithubUserAdapter.setOnItemClickCallback(object : ListGithubUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserDetail) {
                TODO("Not yet implemented")
            }
        })

    }
    private fun showLoading(b: Boolean) {
        if (b) {
            binding.progressBar2.visibility = View.VISIBLE
        }else {
            binding.progressBar2.visibility = View.GONE
        }
    }
}