package com.empresa.abc.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

object RetrofitAdapter {
    private var retrofit: Retrofit? = null
    private val gson: Gson = GsonBuilder().setLenient().create()

    fun getClient(url: String): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit!!
    }
}