package com.example.testapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testapp.model.NewsList
import java.lang.Exception

typealias NewsPageLoader = suspend (pageElement: Int, page: Int) -> List<NewsList>

class NewsPaging(
    private val loader: NewsPageLoader,
    private val pageSize: Int
): PagingSource<Int, NewsList>() {
    override fun getRefreshKey(state: PagingState<Int, NewsList>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return  page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsList> {
        val  pageIndex = params.key ?: 0

        return try {
            val  news = loader.invoke(pageIndex, params.loadSize)
            return LoadResult.Page(
                data = news,
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                nextKey = if (news.size == params.loadSize) pageIndex + (params.loadSize / pageSize) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}