package com.koombea.posts.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    var uid: String,
    var name: String,
    var email: String,
    var profile_pic: String,
    var post: Post
) {
}