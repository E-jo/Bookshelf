package com.example.bookshelf.data

import android.util.Log
import com.example.bookshelf.models.Book
import com.example.bookshelf.network.BookGeneralApiService

interface BookshelfRepository {
    suspend fun getBooks(query: String): List<Book>
}

var query = "jazz+history"
var maxResults = 40

class BookshelfRepositoryImpl(
    private val bookGeneralApiService: BookGeneralApiService
) : BookshelfRepository {
    override suspend fun getBooks(query: String): List<Book> {
        val response = bookGeneralApiService.getBooks(query, maxResults)
        val items = response.get("items").asJsonArray
        val books: MutableList<Book> = mutableListOf()

        Log.d("getBooks", "Items retrieved: ${items.size()}")

        items.forEach { it ->
            Log.d("getBooks", it.toString())
            val volumeInfo = it.asJsonObject?.get("volumeInfo")?.asJsonObject
            if (volumeInfo == null) {
                Log.e("getBooks", "volumeInfo is null for item: $it")
                return@forEach
            }

            Log.d("getBooks", volumeInfo.toString())

            val imageLinks = volumeInfo.get("imageLinks")?.asJsonObject

            Log.d("getBooks", volumeInfo.toString())
            Log.d("getBooks", imageLinks.toString())

            val title = volumeInfo.get("title")?.asString
            val authors = volumeInfo.get("authors")?.asJsonArray
                ?.joinToString(", ") { it.asString }
            val publisher = volumeInfo.get("publisher")?.asString
            val description = volumeInfo.get("description")?.asString
            val pageCount = volumeInfo.get("pageCount")?.asInt

            Log.d("getBooks", "Title: $title")
            Log.d("getBooks", "Authors: $authors")
            Log.d("getBooks", "Publisher: $publisher")
            Log.d("getBooks", "Description: $description")
            Log.d("getBooks", "Page count: $pageCount")

            val smallThumbnail = imageLinks?.get("smallThumbnail")?.asString
            val thumbnail = imageLinks?.get("thumbnail")?.asString

            Log.d("getBooks", "Small thumbnail: $smallThumbnail")
            Log.d("getBooks", "Thumbnail: $thumbnail")

            books.add(Book(
                title = title ?: "Unknown Title",
                authors = authors ?: "Unknown Author",
                description = description ?: "No Description",
                thumbnail = thumbnail
            ))
        }

        books.forEach {
            Log.d("getBooks", it.toString())
        }

        return books
    }
}

