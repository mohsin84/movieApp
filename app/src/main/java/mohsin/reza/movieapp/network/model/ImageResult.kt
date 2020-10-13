package mohsin.reza.movieapp.network.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class ImageResult(
    @Json(name = "images") val images: Images
) : Parcelable