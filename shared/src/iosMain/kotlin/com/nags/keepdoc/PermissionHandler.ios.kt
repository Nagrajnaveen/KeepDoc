package com.nags.keepdoc

import androidx.compose.runtime.Composable

@Composable
actual fun RequestCameraAndFilePermissions(
    onPermissionsGranted: @Composable () -> Unit
) {
    onPermissionsGranted()
}
