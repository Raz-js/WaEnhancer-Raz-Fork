package com.wmods.wppenhacer.gmail

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

data class ThreadsListResponse(val threads: List<ThreadItem>?, val resultSizeEstimate: Int?)
data class ThreadItem(val id: String, val snippet: String?)
data class ThreadDetail(val id: String, val messages: List<MessageItem>?)
data class MessageItem(val id: String, val snippet: String?)

interface GmailService {
    @GET("/gmail/v1/users/me/threads")
    fun listThreads(@Query("maxResults") max: Int = 100): Call<ThreadsListResponse>

    @GET("/gmail/v1/users/me/threads/{id}")
    fun getThread(@Path("id") id: String): Call<ThreadDetail>

    @POST("/gmail/v1/users/me/messages/send")
    fun sendMessage(@Body body: Map<String, String>): Call<Void>
}
