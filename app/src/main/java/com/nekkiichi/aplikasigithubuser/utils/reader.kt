package com.nekkiichi.aplikasigithubuser.utils

import android.content.Context
import com.beust.klaxon.Klaxon
import com.nekkiichi.aplikasigithubuser.datasGithubUser.GithubUser

fun getGithubLists(context: Context): ArrayList<GithubUser> {
    val jsonString: String =
        context.assets.open("githubuser.json").bufferedReader(Charsets.UTF_8).use { it.readText() }
    val result = Klaxon().parseArray<GithubUser>(jsonString)

    if (result != null) {
        val arraylist = ArrayList(result)
        return arraylist
    } else {
        return arrayListOf()
    }
}