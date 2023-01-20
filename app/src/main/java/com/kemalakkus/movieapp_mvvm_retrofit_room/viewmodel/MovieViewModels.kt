package com.kemalakkus.movieapp_mvvm_retrofit_room.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kemalakkus.movieapp_mvvm_retrofit_room.model.Genres
import com.kemalakkus.movieapp_mvvm_retrofit_room.model.Movie
import com.kemalakkus.movieapp_mvvm_retrofit_room.model.MovieResponse
import com.kemalakkus.movieapp_mvvm_retrofit_room.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModels(app: Application): AndroidViewModel(app) {
    val repository= MovieRepository(app)
    val popularMovie= MutableLiveData<MovieResponse>()
    val recentMovie= MutableLiveData<MovieResponse>()
    val genresMovie= MutableLiveData<Genres>()
    val loading= MutableLiveData<Boolean>()
    var page=1



    fun getPopularMovies()=viewModelScope.launch {
        loading.value=true
        val response = repository.getPopularMovie(page.toString())
        if (response.isSuccessful){
            response.body()?.let {
                popularMovie.value=it


            }
        }

    }
    fun getRecentMovies()=viewModelScope.launch {

        val response=repository.getRecentMovie(page.toString())
        if (response.isSuccessful){
            response.body()?.let {
                recentMovie.value=it
            }
        }
    }

    fun getGenres()=viewModelScope.launch {

        val response= repository.getGenres()
        if (response.isSuccessful){
            response.body()?.let {
                genresMovie.value=it
            }
        }
    }
    fun insertMovie(movie: Movie)=viewModelScope.launch {
        repository.insertMovie(movie)
    }
    fun delete(movie: Movie)=viewModelScope.launch {
        repository.deleteMovie(movie)
    }
    fun readMovies(): LiveData<List<Movie>> {
        return repository.readMovies()
    }

}