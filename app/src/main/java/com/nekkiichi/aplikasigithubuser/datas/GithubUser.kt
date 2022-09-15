package com.nekkiichi.aplikasigithubuser.datasGithubUser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser (
    var name: String,
    var username: String,
    var company: String,
    var location: String,
    var img_url: String,
    var following_count: Int,
    var follower_count: Int,
    var repo_count: Int
    ): Parcelable