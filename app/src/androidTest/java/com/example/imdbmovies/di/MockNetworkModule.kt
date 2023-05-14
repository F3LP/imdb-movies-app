package com.example.imdbmovies.di

import com.example.imdbmovies.data.di.NetworkModule
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
internal class MockNetworkModule: NetworkModule() {

    override var baseUrl: String = "http://127.0.0.1:8080"
}