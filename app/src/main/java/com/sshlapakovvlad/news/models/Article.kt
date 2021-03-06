package com.sshlapakovvlad.news.models

data class Article(
    val title: String,
    val urlToImage: String,
    val snippet: String,
    val index: Int,
    val url: String,
    val timeToRead: Int,
    val sentiment: String
)
