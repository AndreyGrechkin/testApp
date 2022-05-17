package com.example.testapp.ui.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.testapp.databinding.NewsLoadStateBinding

class NewsLoadStateAdapter(
    private val retry: () -> Unit
): LoadStateAdapter<NewsLoadStateAdapter.Holder>() {

    override fun onBindViewHolder(holder: Holder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): Holder {
        return Holder(
            NewsLoadStateBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false
            ), null, retry
        )
    }

    class Holder(
        private val binding: NewsLoadStateBinding,
        private val  swipeRefreshLayout: SwipeRefreshLayout?,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonRetry.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) {
            binding.textErrorMessage.isVisible = loadState is LoadState.Error
            binding.buttonRetry.isVisible = loadState is LoadState.Error
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.isRefreshing = loadState is LoadState.Loading
                binding.progressBar.isVisible = false
            } else {
                binding.progressBar.isVisible = loadState is LoadState.Loading
            }
        }
    }


}