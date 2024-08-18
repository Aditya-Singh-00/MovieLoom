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

    init {
        getMovies()
    }

    private fun getMovies() {
        _uiState.value = HomeScreenState.Loading
        viewModelScope.launch {
            val items = listOf(
                MoviesWithGenre("Sci-Fi", repository.getMovies("sci-fi")),
                MoviesWithGenre("Fantasy", repository.getMovies("fantasy")),
                MoviesWithGenre("Animation", repository.getMovies("animation")),
                MoviesWithGenre("Horror", repository.getMovies("horror"))
            )
            if (items.isEmpty()) {
                _uiState.value = HomeScreenState.Error("Nothing to show")
            } else {
                _uiState.value = HomeScreenState.Success(items)
            }
        }
    }
}