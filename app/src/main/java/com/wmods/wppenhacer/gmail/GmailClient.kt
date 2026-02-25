package com.wmods.wppenhacer.gmail

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GmailClient private constructor(private val service: GmailService) {
    companion object {
        fun create(accessToken: String): GmailClient {
            val authHeader = "Bearer $accessToken"
            val client = OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    val original: Request = chain.request()
                    val request = original.newBuilder()
                        .header("Authorization", authHeader)
                        .build()
                    chain.proceed(request)
                })
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://gmail.googleapis.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return GmailClient(retrofit.create(GmailService::class.java))
        }
    }

    fun listThreads() = service.listThreads(100)
    fun getThread(id: String) = service.getThread(id)
    fun sendMessage(rawBody: String) = service.sendMessage(mapOf("raw" to rawBody))
}
