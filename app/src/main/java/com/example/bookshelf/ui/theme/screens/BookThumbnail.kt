package com.example.bookshelf.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.models.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookThumbnail(
    book: Book,
    onItemClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = modifier.padding(
            top = 0.dp,
            start = 0.dp,
            end = 12.dp,
            bottom = 12.dp),
        onClick = { onItemClick(book) }
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(book.thumbnail ?: R.drawable.ic_broken_image)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(id = R.string.book_photo),
                onError = { error ->
                    error.result.throwable.localizedMessage?.let {
                        Log.d("AsyncImage", it)
                    }
                },
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}