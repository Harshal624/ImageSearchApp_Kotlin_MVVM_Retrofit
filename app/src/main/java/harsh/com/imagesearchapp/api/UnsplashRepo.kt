package harsh.com.imagesearchapp.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import harsh.com.imagesearchapp.data.UnSplashPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepo @Inject constructor(private val unsplashAPI: UnsplashAPI) {

    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UnSplashPagingSource(unsplashAPI, query)
            }
        ).liveData

}