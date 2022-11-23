package com.nekkiichi.aplikasigithubuser.data.remote.response

import com.google.gson.annotations.SerializedName
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserItem

data class ResponseUsers(
    @SerializedName("total_count") var totalCount: Int? = null,
    @SerializedName("incomplete_results") var incompleteResults: Boolean? = null,
    @SerializedName("items") var items: List<UserItem> = arrayListOf()
)