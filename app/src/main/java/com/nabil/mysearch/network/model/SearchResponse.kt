package com.nabil.mysearch.network.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(@SerializedName("items")
                          val items: List<SearchItem>?)