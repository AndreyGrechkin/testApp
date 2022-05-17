package com.example.testapp.reposytory

import androidx.paging.PagingData
import com.example.testapp.model.NewsList
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getPageNews(): Flow<PagingData<NewsList>>
}