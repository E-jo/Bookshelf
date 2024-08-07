package com.example.bookshelf.ui.theme.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf.ui.theme.BookshelfUiState
import com.example.bookshelf.ui.theme.BookshelfViewModel
import com.example.bookshelf.ui.theme.ScreenState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    bookshelfUiState: BookshelfUiState,
    bookshelfScreenState: State<ScreenState>,
    contentPadding: PaddingValues = PaddingValues(6.dp),
) {
    val viewModel: BookshelfViewModel = viewModel()

    var searchText by rememberSaveable { mutableStateOf("") }

    if (bookshelfScreenState.value.showDialog) {
        SearchDialog(
            searchText = searchText,
            onSearchTextChange = { searchText = it },
            onDismissRequest = { viewModel.dismissDialog() },
            onSearch = {
                viewModel.dismissDialog()
                viewModel.bookshelfUiState = BookshelfUiState.Loading
                viewModel.getBooks(searchText)
                viewModel.navigateToThumbnailPage()
            }
        )
    }

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
            onItemClick = { }
        )
    }

}



