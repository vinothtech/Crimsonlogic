package com.crimsonlogic.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class MoviesEntity {
    @ColumnInfo(name = "Title")
    val title: String = ""
    @ColumnInfo(name = "Year")
    val year: String = ""
    @PrimaryKey
    @ColumnInfo(name = "imdbID")
    val imDB: String = ""
    @ColumnInfo(name = "Type")
    val type: String = ""
    @ColumnInfo(name = "Poster")
    val poster: String = ""
    @ColumnInfo(name = "Language")
    val language: String = ""
    @ColumnInfo(name = "Plot")
    val plot: String = ""
    @ColumnInfo(name = "Genre")
    val genre: String = ""
    @ColumnInfo(name = "Released")
    val released: String = ""
    @ColumnInfo(name = "Rated")
    val rated: String = ""
}