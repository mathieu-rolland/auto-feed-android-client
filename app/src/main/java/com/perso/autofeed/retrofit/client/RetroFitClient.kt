package com.perso.autofeed.retrofit.client

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroFitClient() {

    //private val client = OkHttpClient()
    companion object {
        const val BASE_URL_LOCAL = "http://MBP-de-Mathieu:8080"
    }

    var client: Retrofit

    init {
        val okHttpClient = OkHttpClient().newBuilder().addInterceptor(AuthenticationInterceptor()).build();
        client = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL_LOCAL)
            .build()
    }




}