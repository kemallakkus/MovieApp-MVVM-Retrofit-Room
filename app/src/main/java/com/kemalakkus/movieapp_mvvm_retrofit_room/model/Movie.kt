package com.kemalakkus.movieapp_mvvm_retrofit_room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Movie(

    @PrimaryKey
    val id:Int?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val genre_ids:List<Int>?,
    val vote_average: Double?,
    val vote_count: Int?,
    val rating:String?,
    val ratingColor:String?
): Serializable {

}