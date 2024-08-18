package com.movieloom.app.presentation.home

import com.movieloom.app.data.models.Movie

sealed class HomeScreenState {
    data class Success(val movies: List<Movie>) : HomeScreenState()
    data class Error(val error: String) : HomeScreenState()
    data object Loading : HomeScreenState()
}