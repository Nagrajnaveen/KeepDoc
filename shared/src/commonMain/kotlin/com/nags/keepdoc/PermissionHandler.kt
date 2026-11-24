package com.nags.keepdoc

import androidx.compose.runtime.Composable

@Composable
expect fun RequestCameraAndFilePermissions(
    onPermissionsGranted: @Composable () -> Unit
)
