package com.opalynskyi.cleanmovies.core.data.movies.entities

data class MovieEntity(
    val id: Int,

    val backdropPath: String?,

    val genreIds: IntArray? = IntArray(0),

    val originalLanguage: String?,

    val originalTitle: String?,

    var overview: String?,

    val releaseDate: String,

    val posterPath: String,

    val popularity: Float,

    val title: String?,

    val isVideo: Boolean,

    val voteAverage: Float,

    val voteCount: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MovieEntity

        if (id != other.id) return false
        if (backdropPath != other.backdropPath) return false
        if (genreIds != null) {
            if (other.genreIds == null) return false
            if (!genreIds.contentEquals(other.genreIds)) return false
        } else if (other.genreIds != null) return false
        if (originalLanguage != other.originalLanguage) return false
        if (originalTitle != other.originalTitle) return false
        if (overview != other.overview) return false
        if (releaseDate != other.releaseDate) return false
        if (posterPath != other.posterPath) return false
        if (popularity != other.popularity) return false
        if (title != other.title) return false
        if (isVideo != other.isVideo) return false
        if (voteAverage != other.voteAverage) return false
        if (voteCount != other.voteCount) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (backdropPath?.hashCode() ?: 0)
        result = 31 * result + (genreIds?.contentHashCode() ?: 0)
        result = 31 * result + (originalLanguage?.hashCode() ?: 0)
        result = 31 * result + (originalTitle?.hashCode() ?: 0)
        result = 31 * result + (overview?.hashCode() ?: 0)
        result = 31 * result + releaseDate.hashCode()
        result = 31 * result + posterPath.hashCode()
        result = 31 * result + popularity.hashCode()
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + isVideo.hashCode()
        result = 31 * result + voteAverage.hashCode()
        result = 31 * result + voteCount
        return result
    }
}