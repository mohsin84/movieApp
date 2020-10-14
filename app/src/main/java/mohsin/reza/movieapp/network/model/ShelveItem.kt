package mohsin.reza.movieapp.network.model

data class ShelveItem(
    val title: String,
    val movieList: List<Movie>
)

enum class GenreType(val genreId: Int, val genreTitle: String) {
    ACTION(28, "Action"),
    ADVENTURE(12, "Adventure"),
    ANIMATION(16, "Animation"),
    COMEDY(35, "Comedy"),
    CRIME(80, "Crime"),
    DOCUMENTARY(99, "Documentary"),
    DRAMA(18, "Drama"),
    FAMILY(10751, "Family"),
    FANTASY(14, "Fantasy"),
    HISTORY(36, "History"),
    HORROR(27, "Horror"),
    MUSIC(10402, "Music"),
    MYSTERY(9648, "Mystery"),
    ROMANCE(10749, "Romance"),
    SCIENCEFICTION(878, "Science Fiction"),
    TVMOVIE(10770, "TV Movie"),
    THRILLER(53, "Thriller"),
    WAR(10752, "War"),
    WESTERN(37, "Western"),
    UNKNOWN(0, "Unknown");

    companion object {
        private val genreTypeMap: Map<Int, GenreType> =
            values().associateBy { it.genreId }

        fun getTitleFromId(id: Int): String {
            val genre = genreTypeMap.getOrElse(id) { UNKNOWN }
            return genre.genreTitle
        }
    }
}