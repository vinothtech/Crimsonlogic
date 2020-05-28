package com.crimsonlogic.ui.details

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crimsonlogic.data.database.MovieDatabase
import com.crimsonlogic.data.model.api.Movies
import com.crimsonlogic.data.remote.AppApiHelper
import com.crimsonlogic.ui.search.SearchViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class DetailsViewModel : AndroidViewModel {

    var movieDetails: MutableLiveData<Movies> = MutableLiveData<Movies>();
    var loadingNewData: MutableLiveData<Boolean> = MutableLiveData();
    var compositeDisposable = CompositeDisposable();
    var apiHelper = AppApiHelper();

//    private lateinit var readTask: ReadMovie

    constructor(application: Application) : super(application)

    fun getDetails(movieName: String?) {
        loadingNewData.value = true;
        compositeDisposable.add(
            apiHelper.getMovieDetails("short", "5d81e1ce", movieName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Movies>() {
                    override fun onSuccess(response: Movies) {
                        loadingNewData.value = false
                        movieDetails.value = response
                    }

                    override fun onError(e: Throwable) {
                        loadingNewData.value = false
//                        readTask = ReadMovie();
//                        readTask.execute(movieName);

                    }
                })
        );
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose();
//        if (readTask != null) {
//            readTask.cancel(true)
//        }
    }

    private inner class ReadMovie : AsyncTask<String, Void, Movies>() {
        override fun doInBackground(vararg imdbId: String): Movies {

            val imdbId = imdbId[0]
            val moviesDAO = MovieDatabase.getInstance(getApplication()).getMoviesDao()
            val movieResult = moviesDAO.getMovie(imdbId)
            Log.d("vinoth", "Data retreived-------" + movieResult);
            return movieResult
        }

        override fun onPostExecute(movieResult: Movies) {
            movieDetails.value = movieResult
        }
    }

}