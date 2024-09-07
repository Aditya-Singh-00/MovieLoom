package com.movieloom.app.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.movieloom.app.data.Routes
import com.movieloom.app.data.models.Movie
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Stable
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopMoviesSection(
    items: ArrayList<Movie>,
    onPageChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberPagerState { items.size }

    LaunchedEffect(state.settledPage) {
        onPageChange(state.settledPage)
    }

    AnimatedVisibility(
        visible = items.isNotEmpty(),
        enter = fadeIn() + expandVertically(),
        exit = shrinkVertically() + fadeOut()
    ) {
        HorizontalPager(
            state = state,
            modifier = modifier.padding(top = 16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageSpacing = 16.dp
        ) { position ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = 1.dp
            ) {
                KamelImage(
                    resource = { asyncPainterResource(Routes.BASE_URL + items[position].largeCoverImage) },
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(240.dp),
                    animationSpec = tween(),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}