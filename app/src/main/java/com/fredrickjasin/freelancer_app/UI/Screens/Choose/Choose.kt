package com.fredrickjasin.freelancer_app.UI.Screens.Choose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.fredrickjasin.freelancer_app.ui.theme.LogIn

@Composable
fun ChooseScreen(){
    Text(
        text = "Welcome Please Choose your Area",
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = LogIn
        )
    )
}