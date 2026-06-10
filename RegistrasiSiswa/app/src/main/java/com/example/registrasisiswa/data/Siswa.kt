package com.example.registrasisiswa.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "siswa")
data class Siswa(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String,
    val nis: String,
    val kelas: String,
    val alamat: String,
    val noTelepon: String
)
