package com.example.bookshelf.ui.theme

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf.ui.theme.screens.HomeScreen

@Composable
fun BookshelfApp() {
    val bookshelfViewModel: BookshelfViewModel = viewModel(factory = BookshelfViewModel.Factory)
    val screenState by bookshelfViewModel.screenState.collectAsState()

    Scaffold(
        topBar = {
            BookshelfAppBar(
                isShowingThumbnailPage = screenState.isShowingThumbnailScreen,
                onBackButtonClick = { bookshelfViewModel.navigateToThumbnailPage() },
                onSearchButtonClick = { bookshelfViewModel.showDialog() }
            )
        }
    ){
        Surface(
        ) {
            HomeScreen(
                bookshelfUiState = bookshelfViewModel.bookshelfUiState,
                bookshelfScreenState = bookshelfViewModel.screenState.collectAsState(),
                contentPadding = it
            )
        }
    }
}
