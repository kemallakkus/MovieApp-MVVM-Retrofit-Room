package com.kemalakkus.movieapp_mvvm_retrofit_room.model

import java.io.Serializable

data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)