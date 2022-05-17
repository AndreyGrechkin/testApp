package com.example.testapp.reposytory

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.testapp.model.NewsList
import com.example.testapp.network.NewsApi
import com.example.testapp.paging.NewsPageLoader
import com.example.testapp.paging.NewsPaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi
): NewsRepository {

    override fun getPageNews(): Flow<PagingData<NewsList>> {
        val loader: NewsPageLoader = { pageIndex, pageSize ->
            getNews(pageIndex, pageSize)
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {NewsPaging(loader, PAGE_SIZE)}
        ).flow
    }

    private suspend fun getNews(pageIndex: Int, pageSize: Int): List<NewsList> =
        withContext(Dispatchers.IO) {
            val offset = pageIndex * pageSize
            return@withContext api.loadNewsList(API_KEY, LOCAL, LANG, pageSize, offset).data
        }

    private companion object {
        private const val PAGE_SIZE = 1
        private const val API_KEY = "1wWQ3B0rs0RARGpm14dzyA2hEWWGKNcnZu2lIwjR"
        private const val LOCAL = "ru"
        private const val LANG = "ru"
    }
}
