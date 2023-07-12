package com.example.exoplayerpractice.select

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exoplayerpractice.ui.theme.ExoPlayerPracticeTheme

@Composable
fun SelectScreen(
    modifier: Modifier = Modifier,
    onFileSelected: (String) -> Unit,
) {
    val fileSelectLauncher = rememberLauncherForActivityResult(
        contract = PickVisualMedia(),
        onResult = { uri ->
            if (uri == null) return@rememberLauncherForActivityResult
            onFileSelected(uri.toString())
        }
    )

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        NetworkUrlDialog(
            onDismissRequest = { showDialog = false },
            onConfirm = { onFileSelected(it) }
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        ),
        modifier = modifier
    ) {
        Button(onClick = {
            fileSelectLauncher.launch(PickVisualMediaRequest(PickVisualMedia.VideoOnly))
        }) {
            Text(text = "Local File")
        }

        Button(onClick = { showDialog = true }) {
            Text(text = "Network File")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetworkUrlDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit,
) {
    var networkUrl by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Enter Network File URL") },
        text = { TextField(value = networkUrl, onValueChange = { networkUrl = it }) },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(networkUrl)
                    onDismissRequest()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Dismiss")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SelectScreenBodyPreview() {
    ExoPlayerPracticeTheme {
        SelectScreen(modifier = Modifier.fillMaxSize(), onFileSelected = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun NetworkUrlDialogPreview() {
    ExoPlayerPracticeTheme {
        NetworkUrlDialog(onDismissRequest = {}, onConfirm = {})
    }
}