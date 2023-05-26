package com.kev.dictionary.feature_dictionary.presentation

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kev.dictionary.R
import com.kev.dictionary.feature_dictionary.domain.model.WordInfo
import java.io.IOException

@Composable
fun WordInfoItem(
    wordInfo: WordInfo
) {
    val context = LocalContext.current
    val audioUrl = wordInfo.phonetics?.firstOrNull()?.audio

    Column(modifier = Modifier.fillMaxSize()) {
        Row {
            IconButton(onClick = {
                val mediaPlayer = MediaPlayer()
                mediaPlayer.setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )

                if (audioUrl.isNullOrEmpty()) {
                    Toast.makeText(
                        context,
                        "Pronunciation unavailable for this word",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    try {
                        mediaPlayer.setDataSource(context, Uri.parse(audioUrl))
                        mediaPlayer.prepareAsync()
                        mediaPlayer.setOnPreparedListener {
                            it.start()
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_volume_up_24),
                    contentDescription = "Pronounce"
                )
            }

//
        }

        Text(
            text = wordInfo.word,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = wordInfo.phonetic,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(16.dp))
        wordInfo.meanings.forEach { meaning ->
            Text(text = meaning.partOfSpeech, fontWeight = FontWeight.Bold)
            meaning.definitions.forEachIndexed { index, definition ->

                Text(text = "${index + 1}. ${definition.definition}")
                Spacer(modifier = Modifier.height(8.dp))
                definition.example?.let { example ->
                    Text(text = "Example: $example")
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
