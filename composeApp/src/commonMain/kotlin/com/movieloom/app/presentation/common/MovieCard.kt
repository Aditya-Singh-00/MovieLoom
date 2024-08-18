package com.movieloom.app.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.movieloom.app.data.Routes
import com.movieloom.app.data.models.Movie
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.width(150.dp).height(200.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 0.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            KamelImage(
                resource = { asyncPainterResource(Routes.BASE_URL + movie.largeCoverImage) },
                contentDescription = movie.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(listOf(Color.Transparent, Color.Black))
                    )
            )
            Column(
                modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).padding(12.dp)
            ) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = movie.title ?: "",
                    color = Color.White,
                    maxLines = 1,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(2.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = movie.synopsis ?: "",
                        color = Color.White.copy(alpha = 0.5f),
                        maxLines = 3,
                        modifier = Modifier.weight(0.7f),
                        fontSize = 10.sp,
                        lineHeight = 11.sp,
                        overflow = TextOverflow.Ellipsis
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .background(
                                color = Color.White,
                                shape = CircleShape
                            )
                            .padding(8.dp),
                        content = {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                modifier = Modifier.size(12.dp).align(Alignment.Center),
                                tint = Color.Black
                            )
                        }
                    )
                }
            }
        }
    }
}