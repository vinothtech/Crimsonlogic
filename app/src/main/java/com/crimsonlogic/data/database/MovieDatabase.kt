package com.crimsonlogic.data.database

import android.content.Context
import android.graphics.Movie
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.crimsonlogic.data.model.api.Movies

@Database(entities = [(Movies::class)], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    companion object {
        lateinit var database: MovieDatabase;
        fun getInstance(context: Context): MovieDatabase {
            if (::database.isInitialized && database != null) {
                return database;
            } else {
                database =
                    Room.databaseBuilder(context, MovieDatabase::class.java, "MoviesDatabase")
                        .build()
            }
            return database;
        }
    }

    abstract fun getMoviesDao(): MoviesDAO

}