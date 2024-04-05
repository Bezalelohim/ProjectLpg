package com.example.projectlpg.ui.home.components

// LocationPermissionDialog.kt

import android.Manifest
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch
// LocationPermissionDialog.kt

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionDialog(onPermissionGranted: () -> Unit) {
    val permissionState = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    val scope = rememberCoroutineScope()

    // Determine if the permission has been granted
    val isPermissionGranted = permissionState.status.isGranted

    if (!isPermissionGranted) {
        AlertDialog(
            onDismissRequest = { /* Handle dialog dismiss */ },
            title = { Text("Location Permission Required") },
            text = {
                Text("This app requires location permission to WORK. Please grant permission.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        scope.launch {
                            permissionState.launchPermissionRequest()
                        }
                    }
                ) {
                    Text("Grant Permission")
                }
            },
        )
    } else {
        onPermissionGranted()
    }
}
