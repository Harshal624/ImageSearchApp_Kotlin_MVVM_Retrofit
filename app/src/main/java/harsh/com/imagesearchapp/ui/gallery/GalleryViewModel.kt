package harsh.com.imagesearchapp.ui.gallery

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import harsh.com.imagesearchapp.api.UnsplashRepo


class GalleryViewModel @ViewModelInject constructor(private val repo: UnsplashRepo) : ViewModel() {
}