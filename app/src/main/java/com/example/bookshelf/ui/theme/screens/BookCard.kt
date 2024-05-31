package com.example.bookshelf.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.models.Book
import com.example.bookshelf.ui.theme.BookshelfTheme

val LocalScreenHeight = compositionLocalOf<Int> { error("No screen height provided") }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookCard(
    book: Book,
    onItemClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    // Get the screen height
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = modifier
            .padding(
            top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 8.dp,
            start = 12.dp,
            end = 12.dp,
            bottom = 12.dp
            )
            .fillMaxWidth(),
        onClick = { onItemClick(book) }
    ) {
        Box(modifier = Modifier.height(screenHeight)) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(45.dp))
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(book.thumbnail ?: R.drawable.ic_broken_image)                        .crossfade(true)
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
                        .padding(
                            end = 16.dp,
                            bottom = 16.dp
                        )
                    /*
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(
                            min = 150.dp,
                            max = 200.dp
                        )
                        .padding(bottom = 8.dp)

                     */
                )
                Text(
                    text = "Author(s): ${book.authors}",
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = book.description,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun BookCardPreview() {
    BookshelfTheme {
        Surface {
            BookCard(
                book = Book("", "", ""),
                onItemClick = {},
            )
        }
    }
}
