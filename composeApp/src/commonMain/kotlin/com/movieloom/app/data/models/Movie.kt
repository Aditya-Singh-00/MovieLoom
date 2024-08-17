package com.movieloom.app.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val backgroundImageOriginal: String?,
    val id: Int,
    @SerialName("imdb_code") val imdbCode: String,
    val largeCoverImage: String?,
    val mediumCoverImage: String,
    val rating: Double,
    val title: String,
    val year: Int
)