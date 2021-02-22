package com.koombea.posts.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Post(
    var id: Int,
    var date: String,
    var pics: List<String>
)