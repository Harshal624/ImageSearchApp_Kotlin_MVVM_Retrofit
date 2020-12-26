package harsh.com.imagesearchapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashPhoto(
    val id: String,
    val description: String?,
    val urls: UnsplashPhotoUrl,
    val user: UnsplashUser
) : Parcelable {
    @Parcelize
    data class UnsplashPhotoUrl(
        val raw: String,
        val full: String,
        val small: String,
        val regular: String,
        val thumb: String
    ) : Parcelable

    @Parcelize
    data class UnsplashUser(
        val name: String,
        val username: String
    ) : Parcelable {
        val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
    }
}