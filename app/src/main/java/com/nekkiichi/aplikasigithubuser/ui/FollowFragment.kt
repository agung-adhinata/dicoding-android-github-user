package com.nekkiichi.aplikasigithubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nekkiichi.aplikasigithubuser.adapter.ListGithubUserAdapter
import com.nekkiichi.aplikasigithubuser.databinding.FragmentFollowBinding
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserDetail
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserItem
import com.nekkiichi.aplikasigithubuser.data.models.DetailViewModel
import kotlinx.coroutines.launch

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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    if(isFollower) {
                        viewModel.userFollowerFlow.collect {
                            retrieveData(it);
                        }
                    }else {
                        viewModel.userFollowingFlow.collect{
                            retrieveData(it)
                        }
                    }
                }
            }
        }
        // Inflate the layout for this fragment
        return binding.root
    }
    private fun retrieveData(data: com.nekkiichi.aplikasigithubuser.data.Result<List<UserItem>>) {
        when (data) {
            com.nekkiichi.aplikasigithubuser.data.Result.loading -> showLoading(true)
            is com.nekkiichi.aplikasigithubuser.data.Result.Error->
            {
                showLoading(false)
            }
            is com.nekkiichi.aplikasigithubuser.data.Result.Success ->
            {
                showLoading(false)
                createRecycleView(data.data)
            }
        }
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