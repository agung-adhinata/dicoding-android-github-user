package com.nekkiichi.aplikasigithubuser.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nekkiichi.aplikasigithubuser.R
import com.nekkiichi.aplikasigithubuser.datas.UserDetail
import com.nekkiichi.aplikasigithubuser.datas.UserItem
import com.nekkiichi.aplikasigithubuser.services.ApiWrapper

class ListGithubUserAdapter(private val listGithubUser: List<UserItem>) :
    RecyclerView.Adapter<ListGithubUserAdapter.ListViewHolder>() {

    interface OnItemClickCallback {
        fun onItemClicked(data: UserItem)
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
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.github_user_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val userItem = listGithubUser[position]
        holder.tvFollowerCount.text = "..."
        holder.tvRepoCount.text = "..."
        // get data when clicked, based on index number recycleview
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listGithubUser[holder.adapterPosition])
        }

        holder.tvName.text = userItem.login
        holder.tvUsername.text = userItem.login

        Glide.with(holder.itemView.context).load(userItem.avatarUrl).into(holder.ivProfile)
        ApiWrapper().getUserDetail(userItem.login.toString(),object : ApiWrapper.OnGetUserListener{
            override fun onGetUserSuccess(item: UserDetail) {
                Log.d("List", "done")
                holder.tvFollowerCount.text = item.followers.toString()
                holder.tvRepoCount.text = item.publicRepos.toString()
            }

            override fun onGetUserFailed(item: String) {
                holder.tvFollowerCount.text = "error"
                holder.tvRepoCount.text = "error"
            }
        })
    }

    override fun getItemCount(): Int {
        return listGithubUser.size
    }


}