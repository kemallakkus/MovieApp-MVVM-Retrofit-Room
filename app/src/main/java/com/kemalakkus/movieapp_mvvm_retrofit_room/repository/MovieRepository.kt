package com.kemalakkus.movieapp_mvvm_retrofit_room.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.kemalakkus.movieapp_mvvm_retrofit_room.api.ApiService
import com.kemalakkus.movieapp_mvvm_retrofit_room.db.MovieDataBase
import com.kemalakkus.movieapp_mvvm_retrofit_room.model.Genres
import com.kemalakkus.movieapp_mvvm_retrofit_room.model.Movie
import com.kemalakkus.movieapp_mvvm_retrofit_room.model.MovieResponse
import retrofit2.Response

class MovieRepository(application: Application) {
    val apıService= ApiService
    val movieDataBase= MovieDataBase(application)

    suspend fun getPopularMovie(page:String): Response<MovieResponse> {
        return apıService.api.getPopularMovie(page)
    }
    suspend fun getRecentMovie(page:String): Response<MovieResponse> {
        return apıService.api.getRecentMovie(page)
    }
    suspend fun getGenres(): Response<Genres> {
        return apıService.api.getGenres()
    }
    suspend fun insertMovie(movie: Movie){
        movieDataBase.movieDao().insert(movie)
    }
    suspend fun deleteMovie(movie: Movie){
        movieDataBase.movieDao().deleteMovie(movie)
    }
    fun readMovies(): LiveData<List<Movie>> {
        return movieDataBase.movieDao().getMovie()
    }

}