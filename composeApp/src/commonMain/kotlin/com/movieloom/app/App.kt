package com.movieloom.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.movieloom.app.data.repository.AppRepository
import com.movieloom.app.data.repository.AppRepositoryImpl
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.DefaultRequest.Plugin.install
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import movieloom.composeapp.generated.resources.Res
import movieloom.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {

        var showContent by remember { mutableStateOf(false) }
        var text by remember { mutableStateOf("Hello, World!") }
        val scope = rememberCoroutineScope()
        LaunchedEffect(true) {
            scope.launch {
                text = AppRepositoryImpl(getClient()).getMovies()
            }
        }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            Text("Api Response: $text")
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}