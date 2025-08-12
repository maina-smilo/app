package com.example.mainapp.models

data class Patient(
    var id: String? = null,
    val name: String? = null,
    val gender: String? = null,
    val phonenumber: String? = null,
    val nationality: String? = null,
    val age: String? = null,
    val next_of_kin: String? = null,
    val diagnosis: String? = null,
    val imageUrl: String? = null
)
