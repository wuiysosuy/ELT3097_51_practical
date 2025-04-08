package com.example.unit5_pathway2_bookshelfproject.ui.theme

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.unit5_pathway2_bookshelfproject.model.Book
import com.example.unit5_pathway2_bookshelfproject.viewmodel.BooksViewModel

@Composable
fun BooksListScreen(viewModel: BooksViewModel = viewModel()) {
    Log.d("BooksListScreen", "BooksListScreen loaded")
    val books = viewModel.booksList

    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        items(books) { book ->
            BookItem(book)
        }
    }
}

@Composable
fun BookItem(book: Book) {
    Row(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()) {
        AsyncImage(
            model = book.volumeInfo.imageLinks.thumbnail,
            contentDescription = book.volumeInfo.title,
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.width(8.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(book.volumeInfo.title, fontWeight = FontWeight.Bold)
            Text(book.volumeInfo.authors.joinToString(", "))
        }
    }
}
