package com.emre.launcher.ui.cards


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emre.launcher.ui.views.BatteryView
import com.emre.launcher.ui.views.GothamText

@Composable
fun EnergyCard(modifier: Modifier) {
    GradientCard(
        modifier = modifier,
        colors = listOf(Color(0xff009990), Color(0xff6a994e))
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.TopStart)
        ) {
            GothamText("Battery Charge", 20.sp, Color.White, FontWeight.Normal)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)
                    .align(Alignment.Center),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BatteryView(0.96f)
                Spacer(Modifier.width(15.dp))
                Column {
                    GothamText("Distance", size = 22.sp, color = Color.White, FontWeight.Bold)
                    Spacer(Modifier.height(25.dp))
                    GothamText("463 KM", size = 32.sp, color = Color.White, FontWeight.Normal)
                }
            }
        }
    }
}