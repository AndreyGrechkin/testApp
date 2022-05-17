package com.example.testapp.ui.news

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testapp.R
import com.example.testapp.databinding.NewsListFragmentBinding
import com.example.testapp.ui.news.adapter.NewsAdapter
import com.example.testapp.ui.news.adapter.NewsLoadStateAdapter
import com.example.testapp.util.autoCleared
import com.example.testapp.util.simpleScan
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsListFragment: Fragment(R.layout.news_list_fragment) {

    private val binding by viewBinding(NewsListFragmentBinding::bind)
    private val viewModel: NewsListViewModel by viewModels()
    private var newsAdapter: NewsAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
    }

    private fun initList() {
        newsAdapter = NewsAdapter()

        val headerAdapter = NewsLoadStateAdapter {newsAdapter.retry()}
        val footerAdapter = NewsLoadStateAdapter {newsAdapter.retry()}
        val concatAdapter = newsAdapter.withLoadStateHeaderAndFooter(
            header = headerAdapter,
            footer = footerAdapter
        )

        binding.apply {
            newsList.setHasFixedSize(true)
            newsList.adapter = concatAdapter
            errorButton.setOnClickListener {
                newsAdapter.retry()
            }
        }

        observePhoto(newsAdapter)
        toRefresh()
        newsLoadState()
        handleScrolling(newsAdapter)
    }

    private fun observePhoto(adapter: NewsAdapter) {
        lifecycleScope.launchWhenStarted {
            viewModel.newsFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun toRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private  fun newsLoadState() {
        newsAdapter.addLoadStateListener { combinedLoadStates ->
            binding.apply {
                newsList.isVisible = combinedLoadStates.source.refresh is LoadState.NotLoading
                progressBarList.isVisible = combinedLoadStates.source.refresh is LoadState.Loading
                swipeRefreshLayout.isRefreshing = false
                errorButton.isVisible = combinedLoadStates.source.refresh is LoadState.Error
                textError.isVisible = combinedLoadStates.source.refresh is LoadState.Error
            }
        }
    }

    private fun handleScrolling(adapter: NewsAdapter) = lifecycleScope.launch {
        getRefreshLoadStateFlow(adapter)
            .simpleScan(count = 2)
            .collectLatest { (previousState, currentState) ->
                if (previousState is LoadState.Loading && currentState is LoadState.NotLoading) {
                    binding.newsList.scrollToPosition(0)
                }
            }
    }

    private fun getRefreshLoadStateFlow (adapter: NewsAdapter): Flow<LoadState> {
        return  adapter.loadStateFlow
            .map { it.refresh }
    }
}