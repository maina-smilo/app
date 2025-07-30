package com.example.mainapp.network

import okhttp3.MultipartBody
import com.example.mainapp.models.CloudinaryResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CloudinaryApi {
    @Multipart
    @POST("v1_1/dz7ejfgbq/image/upload")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("upload_present") uploadPresent : RequestBody
    ): Response<CloudinaryResponse>


}