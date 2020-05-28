package com.crimsonlogic.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.crimsonlogic.data.model.api.Movies

@Dao
interface MoviesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movie: List<Movies>): List<Long>

    @Query("select * from movies where title=:imDB")
    fun getMovie(imDB: String): Movies


}