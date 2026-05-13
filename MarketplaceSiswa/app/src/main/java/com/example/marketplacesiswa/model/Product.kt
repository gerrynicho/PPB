package com.example.marketplacesiswa.model

import java.util.UUID

data class Product(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val price: String,
    val description: String
)
