package com.emre.launcher.ui.views

import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun MapsView(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp*0.96
    val screenWidth = configuration.screenWidthDp*0.7

    Card(
        modifier = Modifier.width(screenWidth.dp).height(screenHeight.dp).padding(20.dp).fillMaxSize(),
        shape = RoundedCornerShape(16.dp),
    ) {
        AndroidView(
            factory = {
                WebView(it).apply {
                    settings.displayZoomControls = true
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.useWideViewPort = true
                    settings.loadWithOverviewMode = true
                    settings.setSupportZoom(true)
                    //settings.userAgentString = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"

                    webViewClient = WebViewClient()
                    webChromeClient = CustomWebChromeClient()
                }
            },
            update = {
                it.loadData(
                    "<iframe src=\"https://www.google.com/maps/embed?" +
                            "pb=!1m14!1m12!1m3!1d29634.220790700376!2d28.788521818089844!3d41.17295517833539!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!5e0!3m2!1str!2str!4v1734547943677!5m2!1str!2str" +
                            "\" width=\"${screenWidth}\" height=\"${screenHeight*0.90}\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\"" +
                            "referrerpolicy=\"no-referrer-when-downgrade\"></iframe>", "text/html", "UTF-8")
            },
            modifier = modifier
        )
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