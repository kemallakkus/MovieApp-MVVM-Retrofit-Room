package com.kemalakkus.movieapp_mvvm_retrofit_room.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {
    companion object{
        private val BASE_URL="https://api.themoviedb.org/3/"
        val api= Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(MovieApi::class.java)
    }
}