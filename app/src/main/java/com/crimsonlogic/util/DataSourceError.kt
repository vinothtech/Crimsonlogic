package com.crimsonlogic.util

import com.crimsonlogic.data.model.api.Movies

interface DataSourceError {

    fun onDatasourceError();
    fun insertNewData(movies:List<Movies>);

}