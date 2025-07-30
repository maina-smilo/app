package com.example.mainapp.data

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.InputStream

class PatientViewModel:ViewModel() {
    fun uploadPatient(imageUri: Uri?, name: String, gender: String, nationality: String, age: String, diagnosis: String, context: Context){
        viewModelScope.launch ( Dispatchers.IO ){
            try{
                val imageUrl =imageUri?.let {uploadToCloudinary(context, it)}
                val ref = FirebaseDatabase.getInstance().getReference("Patients").push()
                val patientData = mapOf(
                    "id" to ref.key,
                    "name" to name,
                    "gender" to gender,
                    "nationality" to nationality,
                    "age" to age,
                    "diagnosis" to diagnosis,
                    "imageUri" to imageUrl
                )
                ref.setValue(patientData).await()
                withContext(Dispatchers.Main){
                    Toast.makeText(context, "Patient uploaded successfully", Toast.LENGTH_LONG).show()
                }
            }catch (e:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context, "Patient not saved", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun uploadToCloudinary(context: Context, uri: Uri): String{
        val contentResolver = context.contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val fileBytes = inputStream?.readBytes() ?: throw Exception("Image read failed")
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("file", "image.jpg",
                RequestBody.create("image/*".toMediaTypeOrNull(),fileBytes))
            .addFormDataPart("upload_present", uploadPresent).build()
        val request = Request.Builder().url(cloudinaryUrl).post(requestBody).build()
        val response = OkHttpClient().newCall(request).execute()
        if(!response.isSuccessful) throw Exception("Upload failed")
        val responseBody = response.body?.string()
        val secureurl = Regex("\"secure_url\":\"(.*?)\"").find(responseBody ?: "")?.groupValues?.get(1)
        return secureurl ?: throw Exception("Upload failed")
    }
}