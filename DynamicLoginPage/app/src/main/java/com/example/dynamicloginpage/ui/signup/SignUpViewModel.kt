package com.example.dynamicloginpage.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dynamicloginpage.data.local.entity.UserEntity
import com.example.dynamicloginpage.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}

class SignUpViewModel(private val repository: UserRepository) : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState.asStateFlow()

    fun onUsernameChange(value: String) {
        _username.value = value
        resetStateIfNeeded()
    }

    fun onPasswordChange(value: String) {
        _password.value = value
        resetStateIfNeeded()
    }

    fun onConfirmPasswordChange(value: String) {
        _confirmPassword.value = value
        resetStateIfNeeded()
    }

    private fun resetStateIfNeeded() {
        if (_registerState.value is RegisterState.Error) {
            _registerState.value = RegisterState.Idle
        }
    }

    fun register() {
        val usernameVal = _username.value.trim()
        val passwordVal = _password.value
        val confirmPasswordVal = _confirmPassword.value

        if (usernameVal.isBlank()) {
            _registerState.value = RegisterState.Error("Username tidak boleh kosong")
            return
        }
        if (passwordVal.isBlank()) {
            _registerState.value = RegisterState.Error("Password tidak boleh kosong")
            return
        }
        if (passwordVal.length < 6) {
            _registerState.value = RegisterState.Error("Password minimal 6 karakter")
            return
        }
        if (passwordVal != confirmPasswordVal) {
            _registerState.value = RegisterState.Error("Konfirmasi password tidak cocok")
            return
        }

        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            if (repository.isUsernameExists(usernameVal)) {
                _registerState.value = RegisterState.Error("Username '$usernameVal' sudah terdaftar")
                return@launch
            }
            repository.insertUser(UserEntity(username = usernameVal, password = passwordVal))
            _registerState.value = RegisterState.Success
        }
    }
}

class SignUpViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignUpViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
