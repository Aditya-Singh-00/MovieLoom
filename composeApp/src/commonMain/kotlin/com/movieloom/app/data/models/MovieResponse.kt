package com.movieloom.app.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    val data: Data?,
    val status: String?,
)

@Serializable
data class Data(
    val limit: Int?,
    @SerialName("movie_count") val movieCount: Int?,
    val movies: List<Movie?>?,
    @SerialName("page_number") val pageNumber: Int?
)

@Serializable
data class Movie(
    @SerialName("background_image_original") val backgroundImageOriginal: String?,
    val id: Int?,
    @SerialName("imdb_code") val imdbCode: String?,
    @SerialName("large_cover_image") val largeCoverImage: String?,
    @SerialName("medium_cover_image") val mediumCoverImage: String?,
    val rating: Double?,
    val title: String?,
    val year: Int?,
    val synopsis: String?,
)

data class MoviesWithGenre(
    val genre: String,
    val movies: List<Movie>,
)