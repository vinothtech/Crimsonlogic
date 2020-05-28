package com.crimsonlogic.data.model.api

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class MoviesList(
    @SerializedName("Search")
    val moviesList: ArrayList<Movies>,
    @SerializedName("totalResults")
    val totlaResult: String = "",
    @SerializedName("Response")
    val response: String = "") {




}