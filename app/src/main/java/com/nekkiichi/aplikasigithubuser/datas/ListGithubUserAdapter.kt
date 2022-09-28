package com.nekkiichi.aplikasigithubuser.datas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nekkiichi.aplikasigithubuser.R
import com.nekkiichi.aplikasigithubuser.datasGithubUser.GithubUser

class ListGithubUserAdapter(private val listGithubUser: ArrayList<GithubUser>) :
    RecyclerView.Adapter<ListGithubUserAdapter.ListViewHolder>() {

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var ivProfile: ImageView = itemView.findViewById(R.id.iv_github_profile)
        var tvName: TextView = itemView.findViewById(R.id.tv_github_name)
//        var tvUsername: TextView = itemView.findViewById(R.id.tv_github_username)
//        var tvFollowerCount: TextView = itemView.findViewById(R.id.tv_github_follower_count)
//        var tvRepoCount: TextView = itemView.findViewById(R.id.tv_github_repo_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.github_user_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val userItem = listGithubUser[position]
        //get img from string '@drawable/photo_name'
//        val profileImgName = userItem.avatar.split("/")[1]
//        val userImgContext = holder.ivProfile.context
//        val userImgId = userImgContext.resources.getIdentifier(profileImgName,"drawable",userImgContext.packageName)

        holder.tvName.text = userItem.name
//        holder.tvUsername.text = userItem.username
//        holder.tvFollowerCount.text = userItem.follower.toString()
//        holder.tvRepoCount.text = userItem.repository.toString()
////        holder.ivProfile.setImageResource(userImgId)
    }

    override fun getItemCount(): Int {
        return listGithubUser.size
    }


}