package com.example.unit5_pathway2_bookshelfproject.model

data class BooksResponse(
    val items: List<Book>
)

data class Book(
    val id: String,
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String,
    val authors: List<String> = emptyList(),
    val imageLinks: ImageLinks = ImageLinks("")
)

data class ImageLinks(
    val thumbnail: String
)