package com.emre.launcher.ui.views


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SpeedView() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp*0.96
    val screenWidth = configuration.screenWidthDp

    val cardHeight = screenHeight * 0.35
    val cardWidth = screenWidth*0.23

    GradientCard(
        modifier = Modifier.width(cardWidth.dp).height(cardHeight.dp),
        colors = listOf(Color(0xff074799), Color(0xff009990))
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
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
                    Speedometer(83.0f)
                }
            }
        }
    }
}