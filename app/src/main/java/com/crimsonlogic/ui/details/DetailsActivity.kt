package com.crimsonlogic.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.crimsonlogic.R
import com.crimsonlogic.data.model.api.Movies
import com.crimsonlogic.databinding.ActivityDetailsBinding
import kotlinx.android.synthetic.main.activity_search.*

class DetailsActivity : AppCompatActivity() {

    var movieTitle: String? = ""
    lateinit var detailsViewModel: DetailsViewModel;
    lateinit var bindingUtil: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingUtil =
            DataBindingUtil.setContentView<ActivityDetailsBinding>(this, R.layout.activity_details);
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        if (intent.extras?.getString("title") != null) {
            movieTitle = intent.extras?.getString("title")
            supportActionBar?.setTitle(movieTitle)
        }
        detailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java);
        setObservables();
        detailsViewModel.getDetails(movieTitle);
    }

    fun setObservables() {
        detailsViewModel.movieDetails.observe(this, object : Observer<Movies> {
            override fun onChanged(movie: Movies?) {
                bindingUtil.data = movie
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
