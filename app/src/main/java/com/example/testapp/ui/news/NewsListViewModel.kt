package com.example.testapp.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.paging.PagingData
import com.example.testapp.model.NewsList
import com.example.testapp.reposytory.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    val newsFlow: Flow<PagingData<NewsList>>
    private val searchBy = MutableLiveData("")

    init {
        newsFlow = searchBy.asFlow()
            .flatMapLatest {
                repository.getPageNews()
            }
    }

    fun refresh() {
        this.searchBy.postValue(this.searchBy.value)
    }
}