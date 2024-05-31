package com.example.bookshelf.ui.theme

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookshelfApplication
import com.example.bookshelf.data.BookshelfRepository
import com.example.bookshelf.data.query
import com.example.bookshelf.models.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface BookshelfUiState {
    data class Success(val books: List<Book>) : BookshelfUiState
    data object Error : BookshelfUiState
    data object Loading : BookshelfUiState
}

class BookshelfViewModel(
    private val bookshelfRepository: BookshelfRepository
) : ViewModel() {

    var bookshelfUiState: BookshelfUiState by mutableStateOf(BookshelfUiState.Loading)

    private val _screenState = MutableStateFlow(
        ScreenState(
            isShowingThumbnailScreen = true
        )
    )
    val screenState: StateFlow<ScreenState> = _screenState

    init {
        getBooks(query)
    }

    fun getBooks(query: String) {
        viewModelScope.launch {
            bookshelfUiState = try {
                BookshelfUiState.Success(bookshelfRepository.getBooks(query))
            } catch (e: Exception) {
                Log.d("getBooks", e.message.toString())
                BookshelfUiState.Error
            }
        }
    }

    fun updateCurrentBook(selectedBook: Book) {
        Log.d("viewModel", "updateCurrentBook")
        _screenState.update {
            it.copy(currentBook = selectedBook)
        }
    }

    fun navigateToThumbnailPage() {
        _screenState.update {
            it.copy(isShowingThumbnailScreen = true)
        }
    }

    fun navigateToDetailPage() {
        Log.d("viewModel", "navigateToDetailPage")
        _screenState.update {
            it.copy(isShowingThumbnailScreen = false)
        }
    }

    fun showDialog() {
        _screenState.update {
            it.copy(showDialog = true)
        }
    }
    fun dismissDialog() {
        _screenState.update {
            it.copy(showDialog = false)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application: BookshelfApplication =
                    this[APPLICATION_KEY] as BookshelfApplication
                val bookshelfRepository: BookshelfRepository =
                    application.container.bookshelfRepository
                BookshelfViewModel(bookshelfRepository)
            }
        }
    }
}

data class ScreenState(
    val isShowingThumbnailScreen: Boolean = true,
    val currentBook: Book = Book("", "", ""),
    val showDialog: Boolean = false
)
