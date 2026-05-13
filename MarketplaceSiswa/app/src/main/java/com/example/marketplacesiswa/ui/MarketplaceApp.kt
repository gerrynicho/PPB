package com.example.marketplacesiswa.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.marketplacesiswa.data.sampleProducts
import com.example.marketplacesiswa.model.Product
import com.example.marketplacesiswa.ui.components.MarketplaceBottomBar
import com.example.marketplacesiswa.ui.components.MarketplaceFloatingActionButton
import com.example.marketplacesiswa.ui.components.MarketplaceTopBar
import com.example.marketplacesiswa.ui.navigation.MarketplaceScreen
import com.example.marketplacesiswa.ui.screens.AddProductScreen
import com.example.marketplacesiswa.ui.screens.HomeScreen
import com.example.marketplacesiswa.ui.screens.ProfileScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketplaceApp() {
    var currentScreen by remember { mutableStateOf(MarketplaceScreen.Home) }
    val productList = remember { mutableStateListOf<Product>() }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (productList.isEmpty()) {
            productList.addAll(sampleProducts)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            MarketplaceTopBar(
                title = currentScreen.title,
                showBackButton = currentScreen == MarketplaceScreen.AddProduct,
                onBackClick = { currentScreen = MarketplaceScreen.Home }
            )
        },
        bottomBar = {
            MarketplaceBottomBar(
                currentScreen = currentScreen,
                onScreenSelected = { currentScreen = it }
            )
        },
        floatingActionButton = {
            MarketplaceFloatingActionButton(
                visible = currentScreen == MarketplaceScreen.Home,
                onClick = { currentScreen = MarketplaceScreen.AddProduct }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {
                MarketplaceScreen.Home -> HomeScreen(productList)
                MarketplaceScreen.AddProduct -> AddProductScreen(
                    onProductAdded = { newProduct ->
                        productList.add(0, newProduct)
                        currentScreen = MarketplaceScreen.Home
                        scope.launch {
                            snackbarHostState.showSnackbar("Produk berhasil ditambahkan!")
                        }
                    }
                )
                MarketplaceScreen.Profile -> ProfileScreen()
            }
        }
    }
}
