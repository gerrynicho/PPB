package com.example.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.ui.screens.DetailScreen
import com.example.newsapp.ui.screens.MainScreen
import com.example.newsapp.viewmodel.NewsViewModel

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val viewModel: NewsViewModel = viewModel()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(
                viewModel = viewModel,
                onArticleClick = { article ->
                    viewModel.selectArticle(article)
                    navController.navigate("detail")
                }
            )
        }
        composable("detail") {
            val article by viewModel.selectedArticle.collectAsState()
            article?.let {
                DetailScreen(
                    article = it,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
