package com.emre.launcher

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.emre.launcher.data.models.AppInfo
import com.emre.launcher.data.repository.AppRepositoryImpl
import com.emre.launcher.domain.usecases.GetLaunchableAppsUseCase
import com.emre.launcher.ui.theme.EmrLauncherTheme
import com.emre.launcher.ui.viewmodels.AppDrawerViewModel
import com.emre.launcher.ui.views.GothamText

class AppDrawerActivity : ComponentActivity() {
    private val repository by lazy {
        AppRepositoryImpl(packageManager)
    }
    private val getLaunchableAppsUseCase by lazy {
        GetLaunchableAppsUseCase(repository)
    }
    private val viewModel by lazy {
        AppDrawerViewModel(getLaunchableAppsUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        viewModel.loadApps()
        setContent {
            AppDrawerScreen(viewModel = viewModel)
        }
    }

    @Composable
    fun AppDrawerScreen(viewModel: AppDrawerViewModel) {
        val apps by viewModel.apps

        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp

        EmrLauncherTheme {
            Box {
                enableEdgeToEdge()

                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.wallp3),
                    contentDescription = "background_image",
                    contentScale = ContentScale.FillBounds
                )

                Scaffold(modifier = Modifier.fillMaxSize().padding(top = 80.dp), containerColor = Color(0x44050505)) { innerPadding ->
                    Column {
                        Spacer(Modifier.height(10.dp))
                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.ic_action_back),
                                colorFilter = ColorFilter.tint(Color.LightGray),
                                contentDescription = "App icon",
                                modifier = Modifier.padding(start = screenWidth*0.07.dp)
                                    .height(48.dp)
                                    .width(48.dp)
                                    .clickable {
                                        finish()
                                    }
                            )
                            Spacer(Modifier.width(screenWidth*0.1.dp).height(5.dp))
                            GothamText("All Apps On Your Car", 36.sp, Color.White, FontWeight.Normal)
                        }
                        Spacer(Modifier.height(30.dp))
                        LazyVerticalGrid(columns = GridCells.Fixed(4)) {
                            items(apps) { app ->
                                AppCard(app = app)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun AppCard(app: AppInfo) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(28.dp)
                .clickable {
                    launchApp(app.packageName)
                }
        ) {
            Image(
                bitmap = drawableToBitmap(app.icon).asImageBitmap(),
                contentDescription = "App Icon",
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .height(75.dp)
                    .width(75.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = app.name,
                modifier = Modifier.padding(top = 5.dp),
                color = Color.LightGray,
                fontSize = 24.sp
            )
        }
    }

    private fun launchApp(packageName: String) {
        try {
            val intent = packageManager.getLaunchIntentForPackage(packageName)
            intent?.let {
                startActivity(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        var bitmap: Bitmap?

        if (drawable is BitmapDrawable) {
            if (drawable.bitmap != null) {
                return drawable.bitmap
            }
        }

        bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Bitmap.createBitmap(
                1,
                1,
                Bitmap.Config.ARGB_8888
            )
        } else {
            Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        }

        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        drawable.draw(canvas)
        return bitmap
    }
}