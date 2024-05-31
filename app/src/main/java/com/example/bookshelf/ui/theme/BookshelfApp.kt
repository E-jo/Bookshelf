package com.example.bookshelf.ui.theme

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf.ui.theme.screens.HomeScreen

@Composable
fun BookshelfApp() {
    Scaffold{
        Surface(
        ) {
            val bookshelfViewModel: BookshelfViewModel = viewModel(factory = BookshelfViewModel.Factory)
            HomeScreen(
                bookshelfUiState = bookshelfViewModel.bookshelfUiState,
                bookshelfScreenState = bookshelfViewModel.screenState.collectAsState(),
                contentPadding = it
            )
        }
    }
}
