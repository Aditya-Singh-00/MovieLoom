package com.movieloom.app.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.movieloom.app.data.Routes
import com.movieloom.app.data.models.Movie
import com.movieloom.app.data.models.MoviesWithGenre
import com.movieloom.app.presentation.common.HorizontalMovieSection
import com.movieloom.app.presentation.common.MovieCard
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = viewModel { HomeScreenViewModel() },
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()
    when (val value = uiState.value) {
        is HomeScreenState.Loading -> HomeScreenLoading(modifier)
        is HomeScreenState.Error -> HomeScreenError(value.error, modifier)
        is HomeScreenState.Success -> HomeScreenSuccess(value.movies, modifier)
    }
}

@Composable
fun HomeScreenLoading(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun HomeScreenError(
    message: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(message)
    }
}

@Composable
fun HomeScreenSuccess(
    items: List<MoviesWithGenre>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(items) { HorizontalMovieSection(it) }
    }
}
