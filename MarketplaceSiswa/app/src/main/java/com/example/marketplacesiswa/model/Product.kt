package com.example.marketplacesiswa.model

data class Product(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val price: String,
    val description: String
)
