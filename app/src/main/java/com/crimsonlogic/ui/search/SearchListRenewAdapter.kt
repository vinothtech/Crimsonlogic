package com.crimsonlogic.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.crimsonlogic.data.model.api.Movies
import com.crimsonlogic.databinding.ItemSearchQueryBinding

class SearchListRenewAdapter(var listener: ItemClickListener) :
    PagedListAdapter<Movies, RecyclerView.ViewHolder>(Movies.DIFF_CALLBACK) {


    interface ItemClickListener {
        fun onItemClicked(movies: Movies?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            ItemSearchQueryBinding.inflate(LayoutInflater.from(parent.context), parent, false);
        return SearchViewHolder(itemBinding);
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SearchViewHolder) {
            holder.bindTo(getItem(position))
            holder.itemView.setOnClickListener({ v -> listener.onItemClicked(getItem(position)) })
        }
    }


    class SearchViewHolder(val binding: ItemSearchQueryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(movie: Movies?) {
            binding.data = movie
        }
    }

}