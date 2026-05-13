package com.example.marketplacesiswa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.marketplacesiswa.ui.MarketplaceApp
import com.example.marketplacesiswa.ui.theme.MarketplaceSiswaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarketplaceSiswaTheme {
                MarketplaceApp()
            }
        }
    }
}
