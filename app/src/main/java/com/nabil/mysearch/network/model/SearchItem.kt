package com.nabil.mysearch.network.model

import com.google.gson.annotations.SerializedName

data class SearchItem(
    @SerializedName("snippet")
    val snippet: String = "",
    @SerializedName("link")
    val link: String = "",
    @SerializedName("title")
    val title: String = ""
)