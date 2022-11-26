package com.nekkiichi.aplikasigithubuser.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
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
                            retrieveData(it)
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
                if(data.data.isEmpty()) {
                    binding.tvEmptyFragment.visibility = View.VISIBLE
                    createRecycleView(listOf())
                }else{
                    createRecycleView(data.data)
                }
            }
        }
    }
    private fun createRecycleView(data: List<UserItem>) {
        val layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL,false)
        val listGithubUserAdapter = ListGithubUserAdapter(data,requireContext().applicationContext)
        binding.rvListUserFollow.layoutManager = layoutManager
        binding.rvListUserFollow.adapter = listGithubUserAdapter
        listGithubUserAdapter.setOnItemClickCallback(object : ListGithubUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserDetail) {
                val newIntent = Intent(activity, DetailsActivity::class.java)
                newIntent.putExtra(DetailsActivity.EXTRA_USER_DETAIL, data)
                startActivity(newIntent)
            }
        })

    }
    private fun showLoading(b: Boolean) {
        if (b) {
            binding.tvEmptyFragment.visibility = View.GONE
            binding.progressBar2.visibility = View.VISIBLE
        }else {
            binding.progressBar2.visibility = View.GONE
        }
    }
}