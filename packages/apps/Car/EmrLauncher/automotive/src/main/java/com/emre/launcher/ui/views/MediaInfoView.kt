package com.emre.launcher.ui.views

import android.content.Context
import android.media.session.MediaSessionManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MediaInfoView(context: Context) {
    val mediaInfo = getMediaInfo(context)

    mediaInfo?.let {
        Card(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Song: ${it.songTitle}", color = Color.Black)
                Text(text = "Artist: ${it.artist}", color = Color.Gray)
                Text(text = "Album: ${it.album}", color = Color.Gray)
            }
        }
    } ?: run {
        Text(text = "No media playing", modifier = Modifier.padding(16.dp))
    }
}

fun getMediaInfo(context: Context): MediaInfo? {
    val mediaSessionManager = context.getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager
    val activeSessions = mediaSessionManager.getActiveSessions(null)

    // İlk aktif oturumu alıyoruz
    val controller = activeSessions.firstOrNull() ?: return null
    val metadata = controller.metadata ?: return null

    // Metadata'dan gerekli bilgileri çekiyoruz
    val title = metadata.getString(android.media.MediaMetadata.METADATA_KEY_TITLE) ?: "Unknown Title"
    val artist = metadata.getString(android.media.MediaMetadata.METADATA_KEY_ARTIST) ?: "Unknown Artist"
    val album = metadata.getString(android.media.MediaMetadata.METADATA_KEY_ALBUM) ?: "Unknown Album"

    return MediaInfo(title, artist, album)
}

data class MediaInfo(val songTitle: String, val artist: String, val album: String)

@Preview(showBackground = true)
@Composable
fun PreviewMediaInfoView() {
    MediaInfoView(context = LocalContext.current)
}
