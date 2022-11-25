package com.nekkiichi.aplikasigithubuser.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nekkiichi.aplikasigithubuser.R
import com.nekkiichi.aplikasigithubuser.data.UserRepository
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserDetail
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserItem
import com.nekkiichi.aplikasigithubuser.data.remote.services.ApiWrapper
import com.nekkiichi.aplikasigithubuser.databinding.GithubUserItemBinding
import javax.inject.Inject

class ListGithubUserAdapter (private val listGithubUser: List<UserItem>, private val context: Context) :
    RecyclerView.Adapter<ListGithubUserAdapter.ListViewHolder>() {
    @Inject
    @JvmField
    var repository: UserRepository? = null

    interface OnItemClickCallback {
        fun onItemClicked(data: UserDetail)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: GithubUserItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        var ivProfile: ImageView = itemView.ivGithubProfile
        var tvName: TextView = itemView.tvGithubName
        var tvUsername: TextView = itemView.tvGithubUsername
        var tvFollowerCount: TextView = itemView.tvGithubFollowerCount
        var tvRepoCount: TextView = itemView.tvGithubRepoCount
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = GithubUserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val userItem = listGithubUser[position]
        holder.tvFollowerCount.text = "..."
        holder.tvRepoCount.text = "..."
        // get data when clicked, based on index number recycleview
        holder.tvName.text = userItem.login
        holder.tvUsername.text = userItem.login

        Glide.with(holder.itemView.context).load(userItem.avatarUrl).into(holder.ivProfile)
        ApiWrapper().getUserDetails(listGithubUser[position].login.toString(), object : ApiWrapper.OnGetUserListener{
            override fun onGetUserSuccess(item: UserDetail) {
                holder.tvRepoCount.text = item.publicRepos.toString()
                holder.tvFollowerCount.text = item.followers.toString()
                holder.itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(item)
                }
            }
            override fun onGetUserFailed(item: String) {
                holder.tvRepoCount.text  = context.getString(R.string.error)
                holder.tvFollowerCount.text = context.getString(R.string.error)
                Log.d(TAG, "error : $item")
            }
        })
    }

    override fun getItemCount(): Int {
        return listGithubUser.size
    }

    companion object {
        const val TAG = "ListGithubUserAdapter"
    }
}