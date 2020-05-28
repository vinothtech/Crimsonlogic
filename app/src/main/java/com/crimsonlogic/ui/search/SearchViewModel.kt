package com.crimsonlogic.ui.search

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.crimsonlogic.data.database.AppDatabaseHelper
import com.crimsonlogic.data.database.MovieDatabase
import com.crimsonlogic.data.datasource.MovieDataSource
import com.crimsonlogic.data.datasource.MovieDatasourceFactory
import com.crimsonlogic.data.model.api.Movies
import com.crimsonlogic.data.model.api.MoviesList
import com.crimsonlogic.data.remote.AppApiHelper
import com.crimsonlogic.util.DataSourceError
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

class SearchViewModel : AndroidViewModel {


    var isFetchingError: MutableLiveData<Boolean> = MutableLiveData();
    var compositeDisposable = CompositeDisposable();
    var dsFactory: MovieDatasourceFactory

    val executor = Executors.newFixedThreadPool(2)
    var searchMovieData: LiveData<PagedList<Movies>>? = null

    var dbHelper: MovieDatabase
//    private lateinit var insertTask: InsertTask

    constructor(application: Application) : super(application)

    init {
        dbHelper = MovieDatabase.getInstance(getApplication())
        dsFactory = MovieDatasourceFactory("", getDatasoruceErrorListener())
        searchMovieData = LivePagedListBuilder(dsFactory, 20)
            .setFetchExecutor(executor)
            .build()
    }


    fun setUpPagedList(query: String) {
        Log.d("vinoth", "New query------   " + query)
        dsFactory.query = query
        dsFactory.create()
        searchMovieData = LivePagedListBuilder(dsFactory, 20)
            .setFetchExecutor(executor)
            .build()
    }

    fun getDatasoruceErrorListener(): DataSourceError {
        return object : DataSourceError {
            override fun onDatasourceError() {
                isFetchingError.value = true
            }

            override fun insertNewData(movies: List<Movies>) {
//                insertTask = InsertTask()
//                insertTask.execute(movies)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose();
//        if (insertTask != null) {
//            insertTask.cancel(true)
//        }
    }


    private inner class InsertTask : AsyncTask<List<Movies>, Void, List<Movies>>() {
        override fun doInBackground(vararg lists: List<Movies>): List<Movies> {

            val moviesList = lists[0]
            val moviesDAO = MovieDatabase.getInstance(getApplication()).getMoviesDao()
            val integers = moviesDAO.insertMovies(moviesList)
            for (i in integers.indices) {
                Log.d("vinoth", "New Data inserted-------" + integers.get(i).toInt());
            }
            return moviesList
        }
        override fun onPostExecute(dogBreeds: List<Movies>) {
            super.onPostExecute(dogBreeds)
        }
    }

}