package com.crimsonlogic.data.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.crimsonlogic.data.model.api.Movies
import com.crimsonlogic.util.DataSourceError


class MovieDatasourceFactory(var query: String?, var datsourceError: DataSourceError) :
    DataSource.Factory<Long, Movies>() {

    lateinit var source: MovieDataSource

    override fun create(): DataSource<Long, Movies> {
        if (::source.isInitialized && source != null) {
            source.invalidate()
            Log.d("vinoth", "New datasource previous invalidated------   ")
        }
        if (query != null) {
            source = MovieDataSource(query!!)
            source.datsourceErrorListener = datsourceError
            Log.d("vinoth", "created new datasource------   ")
            return source
        }
        return source

    }

}