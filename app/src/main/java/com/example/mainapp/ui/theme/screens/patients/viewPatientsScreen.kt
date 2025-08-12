package com.example.mainapp.ui.theme.screens.patients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.mainapp.data.PatientViewModel
import com.example.mainapp.models.Patient

@Composable
fun PatientListScreen(navController: NavController){
    val patientViewModel: PatientViewModel = viewModel()
    val patients = patientViewModel.patients
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        patientViewModel.fetchPatients(context)
    }
    LazyColumn {
        items(patients){ patient ->
            PatientCard(patient= patient, onDelete ={ patientId ->patientViewModel.deletePatient(patientId , context)},
                navController)
        }
    }
}
@Composable
fun PatientCard(patient: Patient, onDelete: (String) -> Unit, navController: NavController){
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog){
        AlertDialog(
            onDismissRequest = {showDialog= false},
            title = { Text("Confirm Delete") },
            text = { Text("Are you sure you want to delete this record?") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    patient.id?.let{onDelete(it)}
                }) {
                    Text("confirm", color = Color.Red)
                }
            },
            dismissButton ={
                TextButton(onClick = {showDialog = false}) {
                    Text("Cancel")
                }
            }
        )
    }

    Card (modifier = Modifier.fillMaxWidth().padding(10.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)){
        Column (modifier = Modifier.padding(8.dp)){
        Row(verticalAlignment = Alignment.CenterVertically){
            patient.imageUrl?.let{imageUrl ->
                AsyncImage(
                    model= imageUrl,
                    contentDescription = "Patient Image",
                    modifier = Modifier.size(64.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = patient.name?: "No name", style = MaterialTheme.typography.titleMedium)
                Text(text = "AGE: ${patient.age}", style = MaterialTheme.typography.bodySmall)
                Text(text = "DIAGNOSIS: ${patient.diagnosis}", style = MaterialTheme.typography.bodySmall)
            }
        }
    Spacer(modifier = Modifier.height(10.dp))
    Row (horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()){
        TextButton(onClick ={navController.navigate("update_patient/${patient.id}")}) {
            Text("Update", color = Color.Green)
        }
        TextButton(onClick = {showDialog = true}) {
            Text("Delete", color = Color.Red)
        }
    }
    }}
}
