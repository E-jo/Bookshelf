package com.example.bookshelf.network

import com.example.bookshelf.models.Book
import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookGeneralApiService {
    @GET("volumes")
    suspend fun getBooks(@Query("q") query: String): JsonObject
}

interface BookDetailApiService {
    @GET("volumes/{id}")
    suspend fun getBook(@Path("id") id: Int): Book
}
