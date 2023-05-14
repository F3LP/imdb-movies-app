package com.example.imdbmovies.domain.repository

import com.example.imdbmovies.data.model.Movie

interface PopularMoviesRepository {

    suspend fun getPopularMovies(): List<Movie>
}