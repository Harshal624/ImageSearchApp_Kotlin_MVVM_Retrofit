package harsh.com.imagesearchapp.api

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepo @Inject constructor(private val unsplashAPI: UnsplashAPI) {
}