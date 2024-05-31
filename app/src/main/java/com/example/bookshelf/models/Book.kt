package com.example.bookshelf.models

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val title: String,
    val description: String,
    val thumbnail: String? = null
) {
}