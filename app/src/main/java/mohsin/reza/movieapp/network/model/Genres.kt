package mohsin.reza.movieapp.network.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Genres(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String
) : Parcelable