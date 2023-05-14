package com.example.imdbmovies.data.di

import com.example.imdbmovies.data.ImdbApi
import com.example.imdbmovies.data.repository.PopularMoviesRepositoryImpl
import com.example.imdbmovies.domain.repository.PopularMoviesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providePopularMoviesRepository(imdbApi: ImdbApi) : PopularMoviesRepository {
        return PopularMoviesRepositoryImpl(imdbApi)
    }
}