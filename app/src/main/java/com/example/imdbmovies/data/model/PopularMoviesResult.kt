package com.example.imdbmovies.data.model


import com.google.gson.annotations.SerializedName

data class PopularMoviesResult(
    @SerializedName("errorMessage")
    val errorMessage: String,
    @SerializedName("items")
    val movies: List<Movie>
)