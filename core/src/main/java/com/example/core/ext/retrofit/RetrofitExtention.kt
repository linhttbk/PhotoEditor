package com.example.core.ext.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


fun <T> Class<T>.buildNetwork(url: String, enableLogging: Boolean,readTimeout:Long = 30,connectTimeout:Long = 30): T {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    val client = OkHttpClient.Builder().readTimeout(readTimeout, TimeUnit.SECONDS)
        .connectTimeout(connectTimeout, TimeUnit.SECONDS)
    val retrofitBuilder = Retrofit.Builder()
    if (enableLogging) {
        client.addInterceptor(interceptor)
    }

    return retrofitBuilder.baseUrl(url)
        .client(client.build())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(this)
}