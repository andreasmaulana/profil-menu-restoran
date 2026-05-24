package com.example.data

data class MenuItem(
    val id: String,
    val name: String,
    val price: Double,
    val formattedPrice: String,
    val description: String,
    val category: String, // "Makanan" atau "Minuman"
    val imageUrl: String,
    val rating: Float
)
