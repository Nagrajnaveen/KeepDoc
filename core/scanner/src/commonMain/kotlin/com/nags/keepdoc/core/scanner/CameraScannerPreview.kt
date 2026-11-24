package com.nags.keepdoc.core.scanner

import androidx.compose.runtime.Composable

@Composable
expect fun CameraScannerPreview(
    onImageCaptured: (ByteArray, Int, Int) -> Unit
)
