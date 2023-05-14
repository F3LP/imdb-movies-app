package com.example.imdbmovies.data.di

import com.example.imdbmovies.data.ApiKeyInterceptor
import com.example.imdbmovies.data.ImdbApi
import com.example.imdbmovies.data.repository.PopularMoviesRepositoryImpl
import com.example.imdbmovies.domain.repository.PopularMoviesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
internal open class NetworkModule {

    open var baseUrl = "https://imdb-api.com/en/API/"

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(ApiKeyInterceptor())
            .build()
    }

    @Provides
    fun provideImdbService(okHttpClient: OkHttpClient): ImdbApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImdbApi::class.java)
    }

}