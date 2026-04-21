package com.example.todolist.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Icon
import androidx.compose.ui.unit.sp
import com.example.todolist.ui.theme.LightBlue
import com.example.todolist.ui.theme.TextGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SectionHeader(modifier: Modifier = Modifier,
                  title: String = "Task",
                  actionText: String? = "See all",
                  icon: ImageVector? = null,
                  ) {
    Row(
        modifier=modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text= title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        if(actionText != null) {
            Text(
                text = actionText,
                fontSize= 14.sp,
                color = LightBlue
            )
        } else if(icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = "Expand",
                tint = TextGray
            )
        }

    }
}