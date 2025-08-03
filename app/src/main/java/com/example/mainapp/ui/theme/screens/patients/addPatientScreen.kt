package com.example.mainapp.ui.theme.screens.patients

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.mainapp.R
import com.example.mainapp.data.PatientViewModel
import com.example.mainapp.navigation.ROUTE_DASHBOARD

@Composable
fun AddPatientScreen(navController: NavController){
    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var phonenumber by remember { mutableStateOf("") }
    var nationality by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var diagnosis by remember { mutableStateOf("") }
    val imageUri = rememberSaveable() { mutableStateOf<Uri?>(null) }
    val patientViewModel: PatientViewModel = viewModel()
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        uri: Uri? -> uri?.let { imageUri.value=it }
    }
    val context = LocalContext.current


    Column(modifier = Modifier.fillMaxWidth().padding(15.dp)) {
        Text(
            text = "ADD NEW PATIENT",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            fontStyle = FontStyle.Normal,
            color = Color.Magenta,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
        Card (shape = CircleShape, modifier = Modifier.padding(10.dp).size(200.dp),){
            AsyncImage(model = imageUri.value ?: R.drawable.ic_person,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp).clickable { launcher.launch("image/*") })
        }
        Text(text = "Upload Photo")
        OutlinedTextField(
            value = name, onValueChange = { name = it },
            label = { Text("Enter Patient Name") },
            placeholder = { Text("Please enter patient name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = gender, onValueChange = { gender = it },
            label = { Text("Enter Patient gender") },
            placeholder = { Text("Please enter patient gender") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = phonenumber, onValueChange = { phonenumber = it },
            label = { Text("Enter Patient phone number") },
            placeholder = { Text("Please enter patient phone number") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = nationality, onValueChange = { nationality = it },
            label = { Text("Enter Patient Nationality") },
            placeholder = { Text("Please enter patient nationality") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = age, onValueChange = { age = it },
            label = { Text("Enter Patient Age") },
            placeholder = { Text("Please enter patient age") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = diagnosis,
            onValueChange = { diagnosis = it },
            label = { Text("Diagnosis") },
            placeholder = { Text("Patient's Diagnosis") },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            singleLine = false
        )
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){
            Button(onClick = {navController.navigate(ROUTE_DASHBOARD)}){ Text(text = "Go Back") }
            Button(onClick = {patientViewModel.uploadPatient(imageUri.value, name, gender, nationality, age, diagnosis, context)}){ Text(text = "Save Patient") }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddPatientScreenPreview(){
    AddPatientScreen(rememberNavController())
}