package com.nekkiichi.aplikasigithubuser.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetail (
    @SerializedName("login"               ) var login             : String?  = null,
    @SerializedName("id"                  ) var id                : Int?     = null,
    @SerializedName("avatar_url"          ) var avatarUrl         : String?  = null,
    @SerializedName("gravatar_id"         ) var gravatarId        : String?  = null,
    @SerializedName("html_url"            ) var htmlUrl           : String?  = null,
    @SerializedName("followers_url"       ) var followersUrl      : String?  = null,
    @SerializedName("following_url"       ) var followingUrl      : String?  = null,
    @SerializedName("starred_url"         ) var starredUrl        : String?  = null,
    @SerializedName("received_events_url" ) var receivedEventsUrl : String?  = null,
    @SerializedName("name"                ) var name              : String?  = null,
    @SerializedName("company"             ) var company           : String?  = null,
    @SerializedName("location"            ) var location          : String?  = null,
    @SerializedName("public_repos"        ) var publicRepos       : Int?     = null,
    @SerializedName("public_gists"        ) var publicGists       : Int?     = null,
    @SerializedName("followers"           ) var followers         : Int?     = null,
    @SerializedName("following"           ) var following         : Int?     = null,
        ):Parcelable