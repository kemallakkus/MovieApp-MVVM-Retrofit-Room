package com.kemalakkus.movieapp_mvvm_retrofit_room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kemalakkus.movieapp_mvvm_retrofit_room.model.Movie

@Database(entities = arrayOf(Movie::class), version = 1)
@TypeConverters(Converters::class)
abstract class MovieDataBase: RoomDatabase() {
    abstract fun movieDao(): Dao

    //Singleton
    companion object{
        @Volatile private var instance: MovieDataBase?=null
        private val lock=Any()
        operator fun invoke(context: Context)= instance?: synchronized(lock){
            instance?: makeDataBase(context).also {
                instance=it
            }
        }
        @Synchronized
        private fun makeDataBase(context: Context)= Room.databaseBuilder(
            context.applicationContext,MovieDataBase::class.java,"moviedatabase").build()



    }
}