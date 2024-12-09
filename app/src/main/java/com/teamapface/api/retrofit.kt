package com.teamapface.api

import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

// Data class for API request and response
data class AnalysisRequest(val image: String) // Base64 encoded image string
data class AnalysisResponse(val predicted_condition: String)

// Define the Retrofit service
interface ApiService {
    @Multipart
    @POST("predict_skin")
    suspend fun analyzeSkinType(
        @Part file: MultipartBody.Part
    ): AnalysisResponse

    @Multipart
    @POST("predict")
    suspend fun analyzeCondition(
        @Part file: MultipartBody.Part
    ): AnalysisResponse
}

// Retrofit instance
object RetrofitClient {
    private const val BASE_URL = "https://skin-detection-api-1098920691523.asia-southeast2.run.app/"

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}