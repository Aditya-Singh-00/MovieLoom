package com.movieloom.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movieloom.app.data.models.MoviesWithGenre
import com.movieloom.app.data.repository.AppRepository
import com.movieloom.app.data.repository.AppRepositoryImpl
import com.movieloom.app.getClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {
    private val repository: AppRepository = AppRepositoryImpl(getClient())

    private val _uiState = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val uiState: StateFlow<HomeScreenState> = _uiState.asStateFlow()

    private val genres = listOf("Sci-Fi", "Fantasy", "Animation", "Horror")

    fun getMovies() {
        _uiState.value = HomeScreenState.Loading
        viewModelScope.launch {
            genres.forEach { genre ->
                val job = launch {
                    val moviesWithGenre = MoviesWithGenre(genre, repository.getMovies(genre.lowercase()))
                    if (moviesWithGenre.movies.isNotEmpty()) {
                        when (uiState.value) {
                            is HomeScreenState.Loading,
                            is HomeScreenState.Error-> {
                                _uiState.value = HomeScreenState.Success(listOf(moviesWithGenre))
                            }
                            is HomeScreenState.Success -> {
                                _uiState.value = HomeScreenState.Success(
                                    (uiState.value as HomeScreenState.Success).movies + moviesWithGenre
                                )
                            }
                        }
                    }
                }
                job.invokeOnCompletion {
                    when (uiState.value) {
                        HomeScreenState.Loading -> {
                            _uiState.value = HomeScreenState.Error("Nothing to show")
                        }
                        else -> { Unit }
                    }
                }
            }
        }
    }
}