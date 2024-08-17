package com.movieloom.app.data.repository

import com.movieloom.app.data.Routes
import com.movieloom.app.data.models.Movie
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText

class AppRepositoryImpl(
    private val httpClient: HttpClient
) : AppRepository {
    override suspend fun getMovies(): String {

        return try {
            httpClient.get(Routes.MOVIES) {
                parameter("page", 1)
                parameter("limit", 2)
                parameter("sort_by","year")
                parameter("order_by","desc")
            }.bodyAsText()
            //return arrayListOf()
        } catch (e: Exception) {
            return e.message ?: "Something went wrong"
        }

    }

}