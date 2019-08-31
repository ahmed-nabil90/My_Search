package com.nabil.mysearch.network

import com.nabil.mysearch.network.model.SearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("/customsearch/v1")
    fun search(
        @Query("q") searchQuery: String,
        @Query("cx") searchEngineId: String = "010978294137565556541:u9eyvy1wncp",
        @Query("key") apiKey: String = "AIzaSyDgKtFR9LqKJUZuGBhUP0SqB2dV6kXaOs4",
        @Query("gl") country: String = "eg"
    ): Observable<SearchResponse>

    companion object {
        const val BASE_URL = "https://www.googleapis.com"

    }
}