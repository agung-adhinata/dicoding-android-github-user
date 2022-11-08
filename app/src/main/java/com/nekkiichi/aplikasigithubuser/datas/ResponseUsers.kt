package com.nekkiichi.aplikasigithubuser.datas

import com.google.gson.annotations.SerializedName

data class ResponseUsers(
    @SerializedName("total_count") var totalCount: Int? = null,
    @SerializedName("incomplete_results") var incompleteResults: Boolean? = null,
    @SerializedName("items") var items: List<UserItem> = arrayListOf()
)