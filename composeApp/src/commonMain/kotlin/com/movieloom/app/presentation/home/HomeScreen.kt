package com.movieloom.app.presentation.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kmpalette.loader.rememberNetworkLoader
import com.kmpalette.rememberDominantColorState
import com.movieloom.app.data.Routes
import com.movieloom.app.data.models.Movie
import com.movieloom.app.data.models.MoviesWithGenre
import com.movieloom.app.presentation.common.HorizontalMovieSection
import io.ktor.http.Url

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = viewModel { HomeScreenViewModel() },
    modifier: Modifier = Modifier
) {
    LaunchedEffect(true) {
        viewModel.getHomeScreenData()
    }
    val uiState = viewModel.uiState.collectAsState()
    when (val value = uiState.value) {
        is HomeScreenState.Loading -> HomeScreenLoading(modifier)
        is HomeScreenState.Error -> HomeScreenError(value.error, modifier)
        is HomeScreenState.Success -> HomeScreenSuccess(value.topMovies, value.movies, modifier)
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
    topMovies: ArrayList<Movie>,
    items: ArrayList<MoviesWithGenre>,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    val currentPosition = remember { mutableStateOf(0) }

    //TODO need to fix this....
    val networkLoader = Array(10) {
        rememberNetworkLoader()
    }
    val dominantColorState = Array(10) {
        rememberDominantColorState(loader = networkLoader[it])
    }

    val bgColor = animateColorAsState(dominantColorState[currentPosition.value].color, tween(2000))

    LaunchedEffect(topMovies.isNotEmpty()) {
        lazyListState.animateScrollToItem(0)
        topMovies.forEachIndexed { index, movie ->
            movie.largeCoverImage?.let {
                dominantColorState[index].updateFrom(Url(Routes.BASE_URL + it))
            }
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize()
            .drawBehind {
                drawRect(
                    brush = Brush.verticalGradient(
                        listOf(
                            bgColor.value,
                            bgColor.value.copy(alpha = 0.8f),
                            bgColor.value.copy(alpha = 0.7f),
                            bgColor.value.copy(alpha = 0.6f),
                            bgColor.value.copy(alpha = 0.5f),
                            bgColor.value.copy(alpha = 0.4f),
                            bgColor.value.copy(alpha = 0.3f),
                        )
                    )
                )
            },
        state = lazyListState
    ) {
        item {
            TopMoviesSection(
                items = topMovies,
                onPageChange = { currentPosition.value = it }
            )
        }
        items(items) { HorizontalMovieSection(it) }
    }
}
