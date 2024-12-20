package com.emre.launcher.ui.cards

import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun MapsCard(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp * 0.7

    var isExpanded by remember { mutableStateOf(false) }
    val webViewHeight = if (isExpanded) screenHeight*0.8.dp else (screenHeight * 0.52).dp

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .width(screenWidth.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column {
                AndroidView(
                    factory = {
                        WebView(it).apply {
                            settings.loadWithOverviewMode = true
                            settings.displayZoomControls = true
                            settings.domStorageEnabled = true
                            settings.javaScriptEnabled = true
                            settings.useWideViewPort = true
                            settings.setSupportZoom(true)
                            webChromeClient = CustomWebChromeClient()
                            webViewClient = WebViewClient()
                        }
                    },
                    update = {
                        it.loadData(
                            "<iframe src=\"https://www.google.com/maps/embed?" +
                                    "pb=!1m14!1m12!1m3!1d29634.220790700376!2d28.788521818089844!3d41.17295517833539!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!5e0!3m2!1str!2str!4v1734547943677!5m2!1str!2str" +
                                    "\" width=\"${screenWidth}\" height=\"${webViewHeight.value*1.35}\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\"" +
                                    "referrerpolicy=\"no-referrer-when-downgrade\"></iframe>", "text/html", "UTF-8")
                    },
                    modifier = Modifier.height(webViewHeight).width(screenWidth.dp)
                )
                Button(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier.align(Alignment.CenterHorizontally).width(screenWidth.dp).height(50.dp),
                    shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
                ) {
                    Text(if (isExpanded) "Collapse" else "Expand")
                }
            }
        }
    }
}

class CustomWebChromeClient : WebChromeClient() {

    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        consoleMessage?.let {
            Log.d("WebViewConsole", "Line ${it.lineNumber()}: ${it.message()} [${it.sourceId()}]")
        }
        return super.onConsoleMessage(consoleMessage)
    }

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        Log.d("WebViewProgress", "Yükleme İlerleme: $newProgress%")
        super.onProgressChanged(view, newProgress)
    }
}
