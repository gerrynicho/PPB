package com.example.registrasisiswa.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.registrasisiswa.data.Siswa
import com.example.registrasisiswa.viewmodel.StudentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: StudentViewModel) {
    val siswaList by viewModel.allSiswa.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var siswaToEdit by remember { mutableStateOf<Siswa?>(null) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Registrasi Siswa") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Tambah Siswa")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(siswaList, key = { it.id }) { siswa ->
                StudentItem(
                    siswa = siswa,
                    onEditClick = { siswaToEdit = it },
                    onDeleteClick = { viewModel.deleteSiswa(it) }
                )
            }
        }
    }

    if (showAddDialog) {
        FormInput(
            siswa = null,
            onConfirm = { nama, nis, kelas, alamat, noTelepon ->
                viewModel.addSiswa(nama, nis, kelas, alamat, noTelepon)
                showAddDialog = false
            },
            onDismiss = { showAddDialog = false }
        )
    }

    siswaToEdit?.let { siswa ->
        FormInput(
            siswa = siswa,
            onConfirm = { nama, nis, kelas, alamat, noTelepon ->
                viewModel.updateSiswa(
                    siswa.copy(
                        nama = nama,
                        nis = nis,
                        kelas = kelas,
                        alamat = alamat,
                        noTelepon = noTelepon
                    )
                )
                siswaToEdit = null
            },
            onDismiss = { siswaToEdit = null }
        )
    }
}
