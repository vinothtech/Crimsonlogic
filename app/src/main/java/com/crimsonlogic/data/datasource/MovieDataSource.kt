package com.crimsonlogic.data.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.crimsonlogic.data.database.AppDatabaseHelper
import com.crimsonlogic.data.model.api.Movies
import com.crimsonlogic.data.model.api.MoviesList
import com.crimsonlogic.data.remote.AppApiHelper
import com.crimsonlogic.util.DataSourceError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MovieDataSource(var searchQuery: String) : PageKeyedDataSource<Long, Movies>() {

    var apiHelper = AppApiHelper();
    lateinit var datsourceErrorListener: DataSourceError

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, Movies>
    ) {
        Log.d("vinoth", "reached load initial....")
        apiHelper.getMoviesList("movie", "5d81e1ce", "1", searchQuery)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<MoviesList>() {
                override fun onSuccess(response: MoviesList) {
                    if (response.response == "True" && response.moviesList?.size > 0) {
                        callback.onResult(response.moviesList, null, 2L);
                        insertDataToDB(response.moviesList)
                    } else {
                        Log.d("vinoth", "received response as false or emptylist........"+response);
                        sendErrorOnDataSource()
                    }
                }

                override fun onError(e: Throwable) {
                    sendErrorOnDataSource()
                    Log.d("vinoth", "error on fetching list........" + e.localizedMessage);
                }
            })

    }

    fun sendErrorOnDataSource() {
        if (::datsourceErrorListener.isInitialized && datsourceErrorListener != null) {
            datsourceErrorListener.onDatasourceError()
        }
    }

    fun insertDataToDB(movies: List<Movies>) {
        if (::datsourceErrorListener.isInitialized && datsourceErrorListener != null) {
            datsourceErrorListener.insertNewData(movies);
        }
    }


    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Movies>) {

        Log.d("vinoth", "reached load after...." + params.key)

        apiHelper.getMoviesList("movie", "5d81e1ce", "" + params.key, searchQuery)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<MoviesList>() {
                override fun onSuccess(response: MoviesList) {
                    if (response.response == "True" && response.moviesList?.size > 0) {
                        callback.onResult(response.moviesList, params.key + 1);
                        insertDataToDB(response.moviesList)
                    } else {
                        Log.d("vinoth", "received response as false or emptylist........"+response);
                        sendErrorOnDataSource()
                    }
                }

                override fun onError(e: Throwable) {
                    sendErrorOnDataSource()
                    Log.d("vinoth", "error on fetching list........" + e.localizedMessage);
                }
            })

    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Movies>) {

    }
}