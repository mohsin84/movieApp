package mohsin.reza.movieapp.network.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Movie(
    @Json(name = "popularity") val popularity: Double,
    @Json(name = "vote_count") val voteCount: Int,
    @Json(name = "video") val video: Boolean,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "id") val id: Int,
    @Json(name = "adult") val adult: Boolean,
    @Json(name = "backdrop_path") val backdropPath: String,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "genre_ids") val genreIds: List<Int>,
    @Json(name = "title") val title: String,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "overview") val overview: String,
    @Json(name = "release_date") val releaseDate: String
) : Parcelable