package com.example.imdbmovies.ui.popularmoviesviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imdbmovies.data.model.Movie
import com.example.imdbmovies.domain.repository.PopularMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(private val popularMoviesRepository: PopularMoviesRepository) :
    ViewModel() {

    val popularMovies: StateFlow<List<Movie>> = flow {
        emit(popularMoviesRepository.getPopularMovies())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )
}