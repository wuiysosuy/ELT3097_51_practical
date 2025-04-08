package com.example.unit5_pathway2_bookshelfproject


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.unit5_pathway2_bookshelfproject.ui.theme.BooksListScreen
import com.example.unit5_pathway2_bookshelfproject.ui.theme.Unit5_pathway2_BookShelfProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Unit5_pathway2_BookShelfProjectTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    BooksListScreen()
                }
            }
        }
    }
}