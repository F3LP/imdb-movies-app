package com.example.imdbmovies.data.repository

import com.example.imdbmovies.data.ImdbApi
import com.example.imdbmovies.data.model.PopularMoviesResult
import com.example.imdbmovies.data.movies
import com.example.imdbmovies.domain.repository.PopularMoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class PopularMoviesRepositoryImplTest {

    private val imdbApi: ImdbApi = mock()

    private lateinit var repository: PopularMoviesRepository

    @Before
    fun setUp() {
        repository = PopularMoviesRepositoryImpl(imdbApi)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `quando requisitar getPopularMovies e sucesso deve retornar lista de filmes`() = runTest {
        whenever(
            imdbApi.getPopularMovies()
        ).thenReturn(PopularMoviesResult("", movies))

        val actual = repository.getPopularMovies()
        assertEquals(movies, actual)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test(expected = Exception::class)
    fun `quando requisitar getPopularMovies e erro deve retornar exceção`() = runTest {
        whenever(
            imdbApi.getPopularMovies()
        ).thenThrow(Exception())

        repository.getPopularMovies()
    }
}