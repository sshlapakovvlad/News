package com.sshlapakovvlad.news.repositories

import com.sshlapakovvlad.news.models.Article
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArticlesRepository {
    private val apiService = Retrofit
        .Builder()
        .baseUrl("https://factfulnews.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    suspend fun getArticles(tabCategory: String): List<Article> {
        return apiService.fetchArticles(tabCategory).articles
    }
}
