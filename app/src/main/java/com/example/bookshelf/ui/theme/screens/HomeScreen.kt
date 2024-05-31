package com.example.bookshelf.ui.theme.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf.ui.theme.BookshelfUiState
import com.example.bookshelf.ui.theme.BookshelfViewModel
import com.example.bookshelf.ui.theme.ScreenState

@Composable
fun HomeScreen(
    bookshelfUiState: BookshelfUiState,
    bookshelfScreenState: State<ScreenState>,
    contentPadding: PaddingValues = PaddingValues(6.dp),
    modifier: Modifier = Modifier
) {
    val viewModel: BookshelfViewModel = viewModel()

    if (bookshelfScreenState.value.isShowingThumbnailScreen) {
        when (bookshelfUiState) {
            is BookshelfUiState.Success -> BookList(
                books = bookshelfUiState.books,
                onClick = {
                    viewModel.updateCurrentBook(it)
                    viewModel.navigateToDetailPage()
                }
            )
            BookshelfUiState.Loading -> LoadingScreen()
            BookshelfUiState.Error -> ErrorScreen()
        }
    } else {
        BookCard(
            book = bookshelfScreenState.value.currentBook,
            onItemClick = {}
        )
    }

}