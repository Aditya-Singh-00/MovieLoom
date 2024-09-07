package com.movieloom.app.presentation.home

import com.movieloom.app.data.models.Movie
import com.movieloom.app.data.models.MoviesWithGenre

sealed class HomeScreenState {
    data class Success(
        val topMovies: ArrayList<Movie>,
        val movies: ArrayList<MoviesWithGenre>
    ) : HomeScreenState()
    data class Error(val error: String) : HomeScreenState()
    data object Loading : HomeScreenState()
}