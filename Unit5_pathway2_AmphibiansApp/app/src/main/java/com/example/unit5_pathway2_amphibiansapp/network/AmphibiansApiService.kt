package com.example.unit5_pathway2_amphibiansapp.network

import com.example.unit5_pathway2_amphibiansapp.model.Amphibian
import retrofit2.http.GET

interface AmphibiansApiService {
    @GET("amphibians")
    suspend fun getAmphibians(): List<Amphibian>
}