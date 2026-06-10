package com.example.registrasisiswa.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.registrasisiswa.data.Siswa

@Composable
fun FormInput(
    siswa: Siswa? = null,
    onConfirm: (nama: String, nis: String, kelas: String, alamat: String, noTelepon: String) -> Unit,
    onDismiss: () -> Unit
) {
    var nama by remember { mutableStateOf(siswa?.nama ?: "") }
    var nis by remember { mutableStateOf(siswa?.nis ?: "") }
    var kelas by remember { mutableStateOf(siswa?.kelas ?: "") }
    var alamat by remember { mutableStateOf(siswa?.alamat ?: "") }
    var noTelepon by remember { mutableStateOf(siswa?.noTelepon ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = if (siswa == null) "Tambah Siswa" else "Edit Siswa") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = nama,
                    onValueChange = { nama = it },
                    label = { Text("Nama") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = nis,
                    onValueChange = { nis = it },
                    label = { Text("NIS") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = kelas,
                    onValueChange = { kelas = it },
                    label = { Text("Kelas") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = alamat,
                    onValueChange = { alamat = it },
                    label = { Text("Alamat") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
                OutlinedTextField(
                    value = noTelepon,
                    onValueChange = { noTelepon = it },
                    label = { Text("No. Telepon") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { if (nama.isNotBlank()) onConfirm(nama, nis, kelas, alamat, noTelepon) }
            ) {
                Text("Simpan")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Batal") }
        }
    )
}
