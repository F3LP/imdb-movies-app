package com.example.imdbmovies.data

import com.example.imdbmovies.data.model.PopularMoviesResult
import retrofit2.http.GET
import retrofit2.http.Path


interface ImdbApi {

    @GET("MostPopularMovies/{api_key}")
    suspend fun getPopularMovies(): PopularMoviesResult

}

