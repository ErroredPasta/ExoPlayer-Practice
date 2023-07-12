package com.example.exoplayerpractice.player

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.exoplayerpractice.ui.theme.ExoPlayerPracticeTheme

@Composable
fun PlayerScreen(
    modifier: Modifier = Modifier,
    mediaUri: String,
) {
    val player = ExoPlayer.Builder(LocalContext.current).build().apply {
        setMediaItem(MediaItem.fromUri(mediaUri))
    }

    DisposableEffect(player) {
        player.prepare()
        onDispose { player.release() }
    }

    PlayerScreenBody(player = player, modifier = modifier)
}

@Composable
fun PlayerScreenBody(
    modifier: Modifier = Modifier,
    player: Player,
) {
    AndroidView(
        factory = {
            PlayerView(it).apply {
                setPlayer(player)
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun PlayerScreenBodyPreview() {
    val player = ExoPlayer.Builder(LocalContext.current)
        .build()

    ExoPlayerPracticeTheme {
        PlayerScreenBody(
            player = player,
            modifier = Modifier.fillMaxSize()
        )
    }
}