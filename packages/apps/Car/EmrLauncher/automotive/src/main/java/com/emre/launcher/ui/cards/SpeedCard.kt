package com.emre.launcher.ui.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emre.launcher.ui.views.GothamText
import com.emre.launcher.ui.views.Speedometer

@Composable
fun SpeedCard(modifier: Modifier, speed: Float) {
    GradientCard(
        modifier = modifier,
        colors = listOf(Color(0xff074799), Color(0xff009990))
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.TopStart)
        ) {
            GothamText("Car Speed", 20.sp, Color.White, FontWeight.Normal)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
                    .align(Alignment.Center),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Speedometer(speed)
            }
        }
    }
}