package com.example.marketplacesiswa.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marketplacesiswa.model.Product
import com.example.marketplacesiswa.ui.components.ProductCard

@Composable
fun HomeScreen(products: List<Product>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Halo, Siswa!", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
            Text(
                text = "Mau belanja apa hari ini?",
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        items(products, key = { it.id }) { product ->
            ProductCard(product)
        }
    }
}
