package com.kemalakkus.movieapp_mvvm_retrofit_room.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.kemalakkus.movieapp_mvvm_retrofit_room.model.Movie

@Dao
interface Dao {
    @Insert(onConflict= OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)
    @Query("SELECT*FROM movie ORDER BY id DESC")
    fun getMovie(): LiveData<List<Movie>>
    @Delete
    suspend fun deleteMovie(movie: Movie)

}