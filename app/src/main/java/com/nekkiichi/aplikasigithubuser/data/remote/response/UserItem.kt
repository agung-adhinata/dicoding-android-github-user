package com.nekkiichi.aplikasigithubuser.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserItem(
    @SerializedName("login"               ) var login             : String?  = null,
    @SerializedName("id"                  ) var id                : Int?     = null,
    @SerializedName("node_id"             ) var nodeId            : String?  = null,
    @SerializedName("avatar_url"          ) var avatarUrl         : String?  = null,
    @SerializedName("gravatar_id"         ) var gravatarId        : String?  = null,
    @SerializedName("url"                 ) var url               : String?  = null,
):Parcelable