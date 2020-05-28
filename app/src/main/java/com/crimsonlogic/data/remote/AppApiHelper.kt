package com.crimsonlogic.data.remote

import com.crimsonlogic.data.model.api.Movies
import com.crimsonlogic.data.model.api.MoviesList
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

class AppApiHelper {

    private val BASE_URL = "http://www.omdbapi.com/"
    private lateinit var apiHelper: ApiHelper;

    constructor() {
        apiHelper = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create<ApiHelper>(ApiHelper::class.java)
    }

    fun getMoviesList(
        type: String, apiKey: String,
        page: String, s: String): Single<MoviesList> {
        return apiHelper.getMoviesList(type,apiKey,page,s);

    }

    fun getMovieDetails(type: String, apiKey: String,
                        title: String?):Single<Movies>{
        return apiHelper.getMovieDetails(type,apiKey,title);
    }

}