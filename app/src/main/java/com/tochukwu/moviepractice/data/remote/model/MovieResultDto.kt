package com.tochukwu.moviepractice.data.remote.model

import com.squareup.moshi.Json


data class MovieResultDto(
    @Json(name = "Search")
    val search: List<MovieDto>? = null,

    @Json(name = "Response")
    val response: String? = null
) {
}


