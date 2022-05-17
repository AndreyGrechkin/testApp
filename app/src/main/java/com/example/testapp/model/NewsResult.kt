package com.example.testapp.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsResult<T> (
    val data: List<T>
)