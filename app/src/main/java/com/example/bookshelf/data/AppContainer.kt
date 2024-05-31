package com.example.bookshelf.data

import com.example.bookshelf.network.BookGeneralApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val bookshelfRepository: BookshelfRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl =
        "https://www.googleapis.com/books/v1/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: BookGeneralApiService by lazy {
        retrofit.create(BookGeneralApiService::class.java)
    }

    override val bookshelfRepository: BookshelfRepository by lazy {
        BookshelfRepositoryImpl(retrofitService)
    }
}
