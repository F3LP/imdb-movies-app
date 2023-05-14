package com.example.imdbmovies.data

import com.example.imdbmovies.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url.toString()
        val newUrl = url.replace("%7Bapi_key%7D", BuildConfig.IMDB_API_KEY)

        val newRequest = request.newBuilder().url(newUrl).build()

        return chain.proceed(newRequest)
    }
}