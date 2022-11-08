package com.nekkiichi.aplikasigithubuser.datas

import com.google.gson.annotations.SerializedName

data class GithubOrganization(
    @SerializedName("login"              ) var login            : String? = null,
    @SerializedName("id"                 ) var id               : Int?    = null,
    @SerializedName("node_id"            ) var nodeId           : String? = null,
    @SerializedName("url"                ) var url              : String? = null,
    @SerializedName("repos_url"          ) var reposUrl         : String? = null,
    @SerializedName("events_url"         ) var eventsUrl        : String? = null,
    @SerializedName("hooks_url"          ) var hooksUrl         : String? = null,
    @SerializedName("issues_url"         ) var issuesUrl        : String? = null,
    @SerializedName("members_url"        ) var membersUrl       : String? = null,
    @SerializedName("public_members_url" ) var publicMembersUrl : String? = null,
    @SerializedName("avatar_url"         ) var avatarUrl        : String? = null,
    @SerializedName("description"        ) var description      : String? = null
)
