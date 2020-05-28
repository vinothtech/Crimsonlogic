package com.crimsonlogic.data.model.api

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class Movies(
    @SerializedName("Title")
    val title: String = "",
    @SerializedName("Year")
    val year: String = "",
    @PrimaryKey
    @SerializedName("imdbID")
    val imDB: String = "",
    @SerializedName("Type")
    val type: String = "",
    @SerializedName("Poster")
    val poster: String = "",
    @SerializedName("Language")
    val language: String = "",
    @SerializedName("Plot")
    val plot: String = "",
    @SerializedName("Genre")
    val genre: String = "",
    @SerializedName("Released")
    val released: String = "",
    @SerializedName("Rated")
    val rated: String = ""
) {
    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<Movies> = object :
            DiffUtil.ItemCallback<Movies>() {
            override fun areItemsTheSame(
                oldItem: Movies,
                newItem: Movies
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Movies,
                newItem: Movies
            ): Boolean {
                return oldItem == (newItem)
            }
        }
    }
}





