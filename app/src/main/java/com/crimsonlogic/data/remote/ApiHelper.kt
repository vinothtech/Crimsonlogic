package com.crimsonlogic.data.remote

import com.crimsonlogic.data.model.api.Movies
import com.crimsonlogic.data.model.api.MoviesList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiHelper {

    @GET("/")
    fun getMoviesList(
        @Query("type") type: String,
        @Query("apikey") apiKey: String,
        @Query("page") page: String,
        @Query("s") s: String
    ): Single<MoviesList>;

    @GET("/")
    fun getMovieDetails(
        @Query("plot") type: String,
        @Query("apikey") apiKey: String,
        @Query("t") title: String?
    ): Single<Movies>;

}