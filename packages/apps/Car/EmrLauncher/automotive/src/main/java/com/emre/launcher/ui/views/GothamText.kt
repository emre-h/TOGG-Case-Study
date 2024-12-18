package com.emre.launcher.ui.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.emre.launcher.R

@Composable
fun GothamText(text: String, size: TextUnit, color: Color, fontWeight: FontWeight) {
    Text(
        fontFamily = FontFamily(
            Font(R.font.gotham_bold, FontWeight.Bold),
            Font(R.font.gotham, FontWeight.Normal)
        ),
        textAlign = TextAlign.Center,
        fontWeight = fontWeight,
        fontSize = size,
        color = color,
        text = text
    )
}