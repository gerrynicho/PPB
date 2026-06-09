package com.example.dynamicloginpage.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dynamicloginpage.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val username: String) : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun onUsernameChange(value: String) {
        _username.value = value
        if (_loginState.value is LoginState.Error || _loginState.value is LoginState.Success) {
            _loginState.value = LoginState.Idle
        }
    }

    fun onPasswordChange(value: String) {
        _password.value = value
        if (_loginState.value is LoginState.Error || _loginState.value is LoginState.Success) {
            _loginState.value = LoginState.Idle
        }
    }

    fun login() {
        val usernameVal = _username.value.trim()
        val passwordVal = _password.value

        if (usernameVal.isBlank()) {
            _loginState.value = LoginState.Error("Username tidak boleh kosong")
            return
        }
        if (passwordVal.isBlank()) {
            _loginState.value = LoginState.Error("Password tidak boleh kosong")
            return
        }

        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val user = repository.login(usernameVal, passwordVal)
            _loginState.value = if (user != null) {
                LoginState.Success(user.username)
            } else {
                LoginState.Error("Username atau password salah")
            }
        }
    }
}

class LoginViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
