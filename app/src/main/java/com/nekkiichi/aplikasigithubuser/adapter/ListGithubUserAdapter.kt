package com.nekkiichi.aplikasigithubuser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nekkiichi.aplikasigithubuser.R
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserDetail
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserItem
import com.nekkiichi.aplikasigithubuser.data.remote.services.ApiService
import com.nekkiichi.aplikasigithubuser.data.remote.services.ApiWrapper
import com.nekkiichi.aplikasigithubuser.databinding.GithubUserItemBinding
import kotlinx.coroutines.CoroutineScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ListGithubUserAdapter (private val listGithubUser: List<UserItem>) :
    RecyclerView.Adapter<ListGithubUserAdapter.ListViewHolder>() {
    @Inject
    var apiService: ApiService? = null

    interface OnItemClickCallback {
        fun onItemClicked(data: UserDetail)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivProfile: ImageView = itemView.findViewById(R.id.iv_github_profile)
        var tvName: TextView = itemView.findViewById(R.id.tv_github_name)
        var tvUsername: TextView = itemView.findViewById(R.id.tv_github_username)
        var tvFollowerCount: TextView = itemView.findViewById(R.id.tv_github_follower_count)
        var tvRepoCount: TextView = itemView.findViewById(R.id.tv_github_repo_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = GithubUserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.github_user_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val userItem = listGithubUser[position]
        holder.tvFollowerCount.text = "..."
        holder.tvRepoCount.text = "..."
        // get data when clicked, based on index number recycleview


        holder.tvName.text = userItem.login
        holder.tvUsername.text = userItem.login

        Glide.with(holder.itemView.context).load(userItem.avatarUrl).into(holder.ivProfile)
    }

    override fun getItemCount(): Int {
        return listGithubUser.size
    }


}