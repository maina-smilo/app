package com.example.mainapp.data

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mainapp.models.Patient
import com.example.mainapp.navigation.ROUTE_DASHBOARD
import com.example.mainapp.navigation.ROUTE_VIEWPATIENT
import com.google.android.gms.tasks.OnFailureListener
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
    val cloudinaryUrl = "https://api.cloudinary.com/v1_1/dz7ejfgbq/image/upload"
    val uploadPreset = "app_images"
    private val _patients = mutableStateListOf<Patient>()
    val patients: List<Patient> = _patients

    fun uploadPatient(imageUri: Uri?, name: String, gender: String, phonenumber:String, next_of_kin: String, nationality: String, age: String, diagnosis: String, context: Context, navController: NavController){
        viewModelScope.launch ( Dispatchers.IO ){
            try{
                val imageUrl =imageUri?.let {uploadToCloudinary(context, it)}
                val ref = FirebaseDatabase.getInstance().getReference("Patients").push()
                val patientData = mapOf(
                    "id" to ref.key,
                    "name" to name,
                    "gender" to gender,
                    "phonenumber" to phonenumber,
                    "next_of_kin" to next_of_kin,
                    "nationality" to nationality,
                    "age" to age,
                    "diagnosis" to diagnosis,
                    "imageUri" to imageUrl
                )
                ref.setValue(patientData).await()
                withContext(Dispatchers.Main){
                    Toast.makeText(context, "Patient uploaded successfully", Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_VIEWPATIENT)
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
            .addFormDataPart("upload_preset", uploadPreset).build()
        val request = Request.Builder().url(cloudinaryUrl).post(requestBody).build()
        val response = OkHttpClient().newCall(request).execute()
        if(!response.isSuccessful) throw Exception("Upload failed")
        val responseBody = response.body?.string()
        val secureUrl = Regex("\"secure_url\":\"(.*?)\"").find(responseBody ?: "")?.groupValues?.get(1)
        return secureUrl ?: throw Exception("Upload failed")
    }
    fun fetchPatients(context: Context){
        val ref = FirebaseDatabase.getInstance().getReference("Patients")
        ref.get().addOnSuccessListener {snapshot ->
            _patients.clear()
            for(child in snapshot.children){
                val patient = child.getValue(Patient::class.java)
                patient?.let {_patients.add(it)}
            }
            OnFailureListener{
                Toast.makeText(context, "Failed to load patients", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun deletePatient(patientId: String, context: Context){
        val ref= FirebaseDatabase.getInstance().getReference("Patients").child(patientId)
        ref.removeValue().addOnSuccessListener {
            _patients.removeAll { it.id == patientId } }
            .addOnFailureListener {
            Toast.makeText(context,"Patient not Deleted", Toast.LENGTH_LONG).show()
        }


    }
    fun updatePatient(patientId: String, imageUri: Uri?, name: String, gender: String,age: String,diagnosis: String, phonenumber: String, next_of_kin: String, nationality: String, context: Context, navController: NavController){
        viewModelScope.launch (Dispatchers.IO){
            try{
                val imageUrl = imageUri?.let{uploadToCloudinary(context, it)}
                val updatePatientData = mapOf(
                    "id" to patientId,
                    "name" to name,
                    "gender" to gender,
                    "phonenumber" to phonenumber,
                    "next_of_kin" to next_of_kin,
                    "nationality" to nationality,
                    "age" to age,
                    "diagnosis" to diagnosis,
                    "imageUri" to imageUrl

                )
                val ref = FirebaseDatabase.getInstance().getReference("Patients").child(patientId)
                ref.setValue(updatePatientData).await()
                fetchPatients(context)
                withContext(Dispatchers.Main){
                    Toast.makeText(context, "Update sucessful", Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_DASHBOARD)
                }

            }catch (e:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context,"update failed", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}