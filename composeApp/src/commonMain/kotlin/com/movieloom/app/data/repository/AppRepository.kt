package com.movieloom.app.data.repository

import com.movieloom.app.data.models.Movie

interface AppRepository {

    suspend fun getMovies(genre: String): List<Movie>


}