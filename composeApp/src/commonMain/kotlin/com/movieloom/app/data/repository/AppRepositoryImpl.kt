package com.movieloom.app.data.repository

import androidx.compose.ui.util.fastMapNotNull
import com.movieloom.app.data.Routes
import com.movieloom.app.data.models.Movie
import com.movieloom.app.data.models.MovieResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class AppRepositoryImpl(
    private val httpClient: HttpClient
) : AppRepository {
    override suspend fun getMovies(): List<Movie> {
        return try {
            httpClient.get(Routes.MOVIES) {
                parameter("page", 1)
                parameter("limit", 2)
                parameter("sort_by","year")
                parameter("order_by","desc")
            }.body<MovieResponse>().data?.movies?.mapNotNull { it } ?: arrayListOf()
        } catch (e: Exception) {
            println(e.message)
            println(e.cause)
            println(e.toString())
            return arrayListOf()
        }
    }

}