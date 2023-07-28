package com.sam.khabri.data.model.response

data class HeadlinesResponse(
    val articles: List<Article>? = null,
    val status: String? = null,
    val totalResults: Int? = null
)