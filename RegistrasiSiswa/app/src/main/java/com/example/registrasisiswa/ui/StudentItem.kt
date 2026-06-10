package com.example.registrasisiswa.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.registrasisiswa.data.Siswa

@Composable
fun StudentItem(
    siswa: Siswa,
    onEditClick: (Siswa) -> Unit,
    onDeleteClick: (Siswa) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = siswa.nama, style = MaterialTheme.typography.titleMedium)
                Text(text = "NIS: ${siswa.nis}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Kelas: ${siswa.kelas}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Alamat: ${siswa.alamat}", style = MaterialTheme.typography.bodySmall)
                Text(text = "No. Telepon: ${siswa.noTelepon}", style = MaterialTheme.typography.bodySmall)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = { onEditClick(siswa) }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit ${siswa.nama}"
                    )
                }
                IconButton(onClick = { onDeleteClick(siswa) }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Hapus ${siswa.nama}"
                    )
                }
            }
        }
    }
}
