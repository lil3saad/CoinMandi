package com.example.coinmandi.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.coinmandi.ui.theme.Typography

@Composable
fun UserMessageBox(modifier: Modifier ,
    message : String,
    icon : ImageVector,
    iconmodifier  : Modifier
){
    Column(modifier = modifier , horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = icon,
            contentDescription = "User Message Icon",
            modifier = iconmodifier,
            tint = Color.Red
        )
        Text(text = message,
            style = Typography.bodyMedium
        )
    }
}