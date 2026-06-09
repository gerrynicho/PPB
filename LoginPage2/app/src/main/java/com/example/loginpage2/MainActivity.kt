package com.example.dynamicloginpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.dynamicloginpage.data.local.database.AppDatabase
import com.example.dynamicloginpage.data.local.entity.UserEntity
import com.example.dynamicloginpage.data.repository.UserRepository
import com.example.dynamicloginpage.ui.login.LoginScreen
import com.example.dynamicloginpage.ui.login.LoginViewModel
import com.example.dynamicloginpage.ui.login.LoginViewModelFactory
import com.example.dynamicloginpage.ui.theme.DynamicLoginPageTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val database by lazy { AppDatabase.getDatabase(applicationContext) }
    private val repository by lazy { UserRepository(database.userDao()) }
    private val viewModel by viewModels<LoginViewModel> { LoginViewModelFactory(repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Isi data user default jika database kosong
        lifecycleScope.launch {
            if (repository.getUserCount() == 0) {
                repository.insertUser(UserEntity(username = "admin", password = "admin123"))
                repository.insertUser(UserEntity(username = "user", password = "password"))
            }
        }

        enableEdgeToEdge()
        setContent {
            DynamicLoginPageTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
