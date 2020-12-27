package harsh.com.imagesearchapp.data

import androidx.paging.PagingSource
import harsh.com.imagesearchapp.api.UnsplashAPI
import retrofit2.HttpException
import java.io.IOException


private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class UnSplashPagingSource(
    private val unsplashAPI: UnsplashAPI,
    private val query: String
) : PagingSource<Int, UnsplashPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val pos = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = unsplashAPI.searchPhotos(query, pos, params.loadSize)
            val photos = response.results

            LoadResult.Page(
                data = photos,
                prevKey = if (pos == UNSPLASH_STARTING_PAGE_INDEX) null else pos - 1,
                nextKey = if (photos.isEmpty()) null else pos + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}