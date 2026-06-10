package com.example.registrasisiswa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.registrasisiswa.data.AppDatabase
import com.example.registrasisiswa.data.Siswa
import com.example.registrasisiswa.data.SiswaDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StudentViewModel(private val siswaDao: SiswaDao) : ViewModel() {

    val allSiswa: StateFlow<List<Siswa>> = siswaDao.getAllSiswa()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addSiswa(nama: String, nis: String, kelas: String, alamat: String, noTelepon: String) {
        val siswa = Siswa(
            nama = nama.trim(),
            nis = nis.trim(),
            kelas = kelas.trim(),
            alamat = alamat.trim(),
            noTelepon = noTelepon.trim()
        )
        viewModelScope.launch { siswaDao.insertSiswa(siswa) }
    }

    fun updateSiswa(siswa: Siswa) {
        viewModelScope.launch { siswaDao.updateSiswa(siswa) }
    }

    fun deleteSiswa(siswa: Siswa) {
        viewModelScope.launch { siswaDao.deleteSiswa(siswa) }
    }

    class Factory(private val database: AppDatabase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return StudentViewModel(database.siswaDao()) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
