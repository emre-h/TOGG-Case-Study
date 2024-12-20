package com.emre.launcher.ui.cards

import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun MapsCard(modifier: Modifier = Modifier) {
    var isExpanded by remember { mutableStateOf(false) }
    isExpanded = true
    Card(
        modifier = modifier.padding(all = 10.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp
        val screenWidth = configuration.screenWidthDp

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
                                "\" width=\"${screenWidth*0.75}\" height=\"${screenHeight*1.5}\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\"" +
                                "referrerpolicy=\"no-referrer-when-downgrade\"></iframe>", "text/html", "UTF-8")
                },
                modifier = Modifier.fillMaxSize()
            )
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
