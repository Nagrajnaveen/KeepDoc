package com.nags.keepdoc.core.scanner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
actual fun CameraScannerPreview(
    onImageCaptured: (ByteArray, Int, Int) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "📷 Camera Viewfinder (iOS Preview)",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    onImageCaptured(byteArrayOf(1, 2, 3, 4, 5), 1080, 1920)
                },
                modifier = Modifier.size(80.dp),
                shape = CircleShape
            ) {
                Text("Capture")
            }
        }
    }
}
