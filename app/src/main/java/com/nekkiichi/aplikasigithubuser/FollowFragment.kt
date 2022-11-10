package com.nekkiichi.aplikasigithubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nekkiichi.aplikasigithubuser.databinding.FragmentFollowBinding
import com.nekkiichi.aplikasigithubuser.datas.UserItem
import com.nekkiichi.aplikasigithubuser.datas.models.DetailViewModel
import com.nekkiichi.aplikasigithubuser.datas.models.MainViewModel
import kotlinx.coroutines.flow.combine

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
        username?.let {
            if(isFollower)
                viewModel.getUserFollower(it)
            else
                viewModel.getUserFollowing(it)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater,container,false)
        if(isFollower) {
            viewModel.userFollower.observe(this.viewLifecycleOwner) {

            }
        }else {
            viewModel.userFollowing.observe(this.viewLifecycleOwner) {
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun createRecycleView(data: List<UserItem>) {
        val layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL,false)
        binding.rvListUserFollow.layoutManager = layoutManager
    }
}