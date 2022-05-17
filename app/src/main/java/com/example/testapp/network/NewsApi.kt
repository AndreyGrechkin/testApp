package com.example.testapp.network

import com.example.testapp.model.NewsList
import com.example.testapp.model.NewsResult
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    // 1wWQ3B0rs0RARGpm14dzyA2hEWWGKNcnZu2lIwjR
    // https://api.thenewsapi.com/v1/news/top?api_token=1wWQ3B0rs0RARGpm14dzyA2hEWWGKNcnZu2lIwjR&locale=us&limit=3
    @GET("top?")
    suspend fun loadNewsList(
        @Query("api_token") key: String,
        @Query("locale") locale: String,
        @Query("language") lang: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ):NewsResult<NewsList>
}