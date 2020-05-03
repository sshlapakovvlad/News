package com.sshlapakovvlad.news.repositories

import com.sshlapakovvlad.news.models.FetchResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/{category}")
    suspend fun fetchArticles(@Path("category")category: String): FetchResponse
}
