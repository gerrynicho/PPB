package com.example.dynamicloginpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dynamicloginpage.data.local.database.AppDatabase
import com.example.dynamicloginpage.data.local.entity.UserEntity
import com.example.dynamicloginpage.data.repository.UserRepository
import com.example.dynamicloginpage.ui.login.LoginScreen
import com.example.dynamicloginpage.ui.login.LoginViewModel
import com.example.dynamicloginpage.ui.login.LoginViewModelFactory
import com.example.dynamicloginpage.ui.signup.SignUpScreen
import com.example.dynamicloginpage.ui.signup.SignUpViewModel
import com.example.dynamicloginpage.ui.signup.SignUpViewModelFactory
import com.example.dynamicloginpage.ui.theme.DynamicLoginPageTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val database by lazy { AppDatabase.getDatabase(applicationContext) }
    private val repository by lazy { UserRepository(database.userDao()) }
    private val loginViewModel by viewModels<LoginViewModel> { LoginViewModelFactory(repository) }
    private val signUpViewModel by viewModels<SignUpViewModel> { SignUpViewModelFactory(repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            if (repository.getUserCount() == 0) {
                repository.insertUser(UserEntity(username = "admin", password = "admin123"))
                repository.insertUser(UserEntity(username = "user", password = "password"))
            }
        }

        enableEdgeToEdge()
        setContent {
            DynamicLoginPageTheme {
                AppNavGraph(
                    loginViewModel = loginViewModel,
                    signUpViewModel = signUpViewModel
                )
            }
        }
    }
}

@Composable
fun AppNavGraph(
    loginViewModel: LoginViewModel,
    signUpViewModel: SignUpViewModel
) {
    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginScreen(
                    viewModel = loginViewModel,
                    onNavigateToSignUp = { navController.navigate("signup") }
                )
            }
            composable("signup") {
                SignUpScreen(
                    viewModel = signUpViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
