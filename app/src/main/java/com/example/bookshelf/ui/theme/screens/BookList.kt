package com.example.bookshelf.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.bookshelf.R
import com.example.bookshelf.models.Book

@Composable
fun BookList(
    books: List<Book>,
    onClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()

    Log.d("BookList", "Size of bookList: ${books.size}")
    for (book in books) {
        Log.d("BookList", "Book: $book.title")
    }

    LazyColumn(
        modifier = modifier
            .padding(
            top = dimensionResource(id = R.dimen.padding_medium),
            start = dimensionResource(id = R.dimen.padding_medium),
            //end = dimensionResource(id = R.dimen.padding_medium),
        ),
        state = scrollState
    ) {
        item {
            Spacer(modifier = Modifier.height(60.dp))
        }
        val bookPairs = books.chunkIntoPairs(2)
        items(bookPairs) { bookPair ->
            Row {
                bookPair.forEach { book ->
                    BookThumbnail(
                        book = book,
                        onItemClick = onClick,
                        modifier = Modifier.weight(1f)
                    )
                }
                if (bookPair.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

fun <T> List<T>.chunkIntoPairs(size: Int): List<List<T>> {
    val chunked = mutableListOf<List<T>>()
    var index = 0
    while (index < this.size) {
        chunked.add(this.subList(
            index,
            kotlin.math.min(index + size, this.size)
            )
        )
        index += size
    }
    return chunked
}

