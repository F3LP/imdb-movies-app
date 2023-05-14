package com.example.imdbmovies.data.repository

import com.example.imdbmovies.data.ImdbApi
import com.example.imdbmovies.data.model.Movie
import com.example.imdbmovies.domain.repository.PopularMoviesRepository
import javax.inject.Inject
import javax.inject.Singleton

class PopularMoviesRepositoryImpl @Inject constructor(private val imdbApi: ImdbApi): PopularMoviesRepository {

    override suspend fun getPopularMovies(): List<Movie> {
        return try {
            val result = imdbApi.getPopularMovies().movies
            result
        } catch (exception: Exception) {
            throw exception
        }
    }
}