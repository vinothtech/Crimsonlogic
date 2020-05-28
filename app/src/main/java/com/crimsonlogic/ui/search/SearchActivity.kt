package com.crimsonlogic.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crimsonlogic.R
import com.crimsonlogic.data.model.api.Movies
import com.crimsonlogic.ui.details.DetailsActivity
import com.crimsonlogic.util.RxObservable
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*
import java.util.concurrent.TimeUnit

class SearchActivity : AppCompatActivity() {

    lateinit var searchAdapter: SearchListRenewAdapter
    lateinit var searchListViewModel: SearchViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        Log.d("vinoth","on create")
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        val layoutManager = LinearLayoutManager(this)
        searchListViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        searchList.layoutManager = layoutManager;

        searchAdapter = SearchListRenewAdapter(object : SearchListRenewAdapter.ItemClickListener {
            override fun onItemClicked(movies: Movies?) {
                startDetailsActivity(movies?.title)
            }
        })

        searchList.adapter = searchAdapter;


        RxObservable.fromView(searchview)
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter(Predicate<String> { text ->
                if (text.isEmpty() || text.length < 3) {
                    false
                } else {
                    true
                }
            })
            .distinctUntilChanged()
            .switchMap(Function<String, ObservableSource<String>> { query ->
                dataFromNetwork(query)
                    .doOnError({ throwable -> })
                    .onErrorReturn({ throwable -> "" })
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer<String> { result ->
                submitList()
                searchListViewModel.setUpPagedList(result)
                searchAdapter.notifyDataSetChanged()

            })

        searchListViewModel.isFetchingError.observe(this, Observer<Boolean> {
            if (it) {
                fetchingResult.visibility = View.VISIBLE
            } else {
                fetchingResult.visibility = View.GONE
            }
        })



    }

    fun startDetailsActivity(movieTitle: String?) {
        val bundle = Bundle();
        bundle.putString("title", movieTitle)
        val intent = Intent(this, DetailsActivity::class.java);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    fun submitList() {
        searchListViewModel.searchMovieData?.observe(this, object : Observer<PagedList<Movies>> {
            override fun onChanged(moviesNewList: PagedList<Movies>?) {
                fetchingResult.visibility = View.GONE
                searchAdapter.submitList(moviesNewList)
                searchAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun dataFromNetwork(query: String): Observable<String> {
        return Observable.just(true)
            .delay(1, TimeUnit.SECONDS)
            .map { query }
    }


    override fun onStop() {
        super.onStop()
        Log.d("vinoth","on stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("vinoth","on destory")
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        Log.d("vinoth","on saved instance state")
    }

    override fun onPause() {
        super.onPause()
        Log.d("vinoth","on pause")
    }

    override fun onStart() {
        super.onStart()
        Log.d("vinoth","on start")
    }


    override fun onResume() {
        super.onResume()
        Log.d("vinoth","on resume")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("vinoth","on restore instance state")
    }






}
