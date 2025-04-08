package com.example.unit5_pathway2_bookshelfproject.network

import com.example.unit5_pathway2_bookshelfproject.model.BooksResponse
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface BooksApiService {
    @GET("volumes?q=android")
    suspend fun getBooks(): BooksResponse
}

object BooksApi {
    val retrofitService: BooksApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApiService::class.java)
    }
}
