package com.example.newsapp.util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

fun formatPublishedAt(publishedAt: String): String {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val date = sdf.parse(publishedAt) ?: return publishedAt
        val diffMs = System.currentTimeMillis() - date.time
        val hours = TimeUnit.MILLISECONDS.toHours(diffMs)
        when {
            hours < 1 -> "${TimeUnit.MILLISECONDS.toMinutes(diffMs)} min ago"
            hours < 24 -> "$hours hours ago"
            else -> "${TimeUnit.MILLISECONDS.toDays(diffMs)} days ago"
        }
    } catch (e: Exception) {
        publishedAt
    }
}
