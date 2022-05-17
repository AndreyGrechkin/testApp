package com.example.testapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsList(
    @Json(name = "uuid")
    val id: String,
    val title: String,
    @Json(name = "image_url")
    val imageUrl: String
)
