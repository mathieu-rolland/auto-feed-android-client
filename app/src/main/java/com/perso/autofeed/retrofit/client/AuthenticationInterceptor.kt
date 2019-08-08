package com.perso.autofeed.retrofit.client

import android.util.Log
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthenticationInterceptor() : Interceptor {

    companion object {
        const val USER = "admin"
        const val PASSWORD = "nimda"
    }

    private val credentials: String = Credentials.basic(USER, PASSWORD)


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("Authorization", credentials).build()
        Log.e("Token : " , authenticatedRequest.header("Authorization"));
        return chain.proceed(authenticatedRequest)
    }

}