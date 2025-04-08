package com.example.unit5_pathway2_bookshelfproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unit5_pathway2_bookshelfproject.model.Book
import com.example.unit5_pathway2_bookshelfproject.network.BooksApi
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.util.Log

class BooksViewModel : ViewModel() {
    var booksList by mutableStateOf<List<Book>>(emptyList())
        private set

    init {
        Log.d("BooksViewModel", "init called")
        getBooks()
    }

    private fun getBooks() {
        viewModelScope.launch {
            try {
                Log.d("BooksViewModel", "init called")
                val result = BooksApi.retrofitService.getBooks()
                booksList = result.items
                Log.d("BooksViewModel", "init called")
            } catch (e: Exception) {
                Log.e("BooksViewModel", "Error: ${e.message}")
            }
        }
    }
}
