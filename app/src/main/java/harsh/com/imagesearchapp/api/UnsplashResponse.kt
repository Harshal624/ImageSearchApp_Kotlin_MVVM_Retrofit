package harsh.com.imagesearchapp.api

import harsh.com.imagesearchapp.data.UnsplashPhoto

data class UnsplashResponse(
    val results: List<UnsplashPhoto>
)