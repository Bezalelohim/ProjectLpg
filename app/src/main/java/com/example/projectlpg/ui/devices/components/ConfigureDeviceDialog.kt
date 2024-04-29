package com.example.projectlpg.ui.devices.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.example.projectlpg.ui.devices.DevicesScreenViewModel

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigureDeviceDialog(viewModel: DevicesScreenViewModel) {
    val showConfigureDialog = viewModel.showConfigureDialog.collectAsState().value
    val existingProfileNames by viewModel.existingProfileNames.observeAsState(initial = emptyList())

    // Remembered states for dropdown menu and text fields
    var selectedProfileName by rememberSaveable { mutableStateOf("") }
    var ipAddress by rememberSaveable { mutableStateOf("") }
    var portNumber by rememberSaveable { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    if (showConfigureDialog) {
        AlertDialog(
            onDismissRequest = { /* Handle dialog dismiss */ },
            title = { Text("Configure Device") },
            text = {
                Column {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = it }
                    ) {
                        TextField(
                            readOnly = true,
                            value = selectedProfileName,
                            onValueChange = { /* ReadOnly TextField, no action needed */ },
                            label = { Text("Profile Name") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            existingProfileNames.forEach { profileName ->
                                DropdownMenuItem(
                                    text = { Text(profileName) },
                                    onClick = {
                                        selectedProfileName = profileName
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                    TextField(
                        value = ipAddress,
                        onValueChange = { ipAddress = it },
                        label = { Text("IP Address") }
                    )
                    TextField(
                        value = portNumber,
                        onValueChange = { portNumber = it },
                        label = { Text("Port Number") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (selectedProfileName.isNotEmpty()) {
                            viewModel.updateDeviceConfiguration(
                                selectedProfileName,
                                ipAddress,
                                portNumber.toIntOrNull() ?: 0
                            )

                        }
                    }
                ) { Text("Update") }
            },
            dismissButton = {
                // Adding a dismiss button
                Button(
                    onClick = {
                        // Logic to close the dialog, usually involves setting a variable that controls the dialog's visibility to false
                        viewModel.closeConfigurationDialog()
                    }
                ) { Text("Cancel") }
            }
        )
    }
}



@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun ErrorDialog(viewModel: DevicesScreenViewModel) {
    val showErrorDialog = viewModel.showErrorDialog.collectAsState().value

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissErrorDialog() },
            title = { Text("Configuration Error") },
            text = { Text("The Device Couldn't be configured, check if the inputs are valid") },
            confirmButton = {
                Button(onClick = { viewModel.dismissErrorDialog() }) {
                    Text("OK")
                }
            }
        )
    }
}
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun ProfileNameInputDialog(viewModel: DevicesScreenViewModel) {
    // Declare profileName outside the if-statement
    var profileName by rememberSaveable { mutableStateOf("") }
    val show = viewModel.showProfileNameDialog.collectAsState().value

    if (show) {
        AlertDialog(
            onDismissRequest = { /* Dialog will not dismiss when clicking outside of it */ },
            title = { Text("New Device Detected") },
            text = {
                TextField(
                    value = profileName,
                    onValueChange = { profileName = it.trim() }, // Trim whitespace
                    label = { Text("Enter Profile Name") },
                    singleLine = true // Optional, for better UX
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.saveDeviceWithProfileName(profileName)
                    },
                    enabled = profileName.isNotBlank() // The button is enabled only if profileName is not blank
                ) {
                    Text("Save")
                }
            }
        )
    }
}



/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun PreviewConfigureDeviceDialog() {
    // Hard-coded states for the preview
    val showConfigureDialog = true // Assume the dialog is always shown in the preview
    val existingProfileNames = listOf("Profile 1", "Profile 2", "Profile 3")

    var selectedProfileName by rememberSaveable { mutableStateOf("") }
    var ipAddress by rememberSaveable { mutableStateOf("") }
    var portNumber by rememberSaveable { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    if (showConfigureDialog) {
        AlertDialog(
            onDismissRequest = { /* Handle dialog dismiss */ },
            title = { Text("Configure Device") },
            text = {
                Column {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = it }
                    ) {
                        TextField(
                            readOnly = true,
                            value = selectedProfileName,
                            onValueChange = { /* ReadOnly TextField, no action needed */ },
                            label = { Text("Profile Name") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            existingProfileNames.forEach { profileName ->
                                DropdownMenuItem(
                                    text = { Text(profileName) },
                                    onClick = {
                                        selectedProfileName = profileName
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                    TextField(
                        value = ipAddress,
                        onValueChange = { ipAddress = it },
                        label = { Text("IP Address") }
                    )
                    TextField(
                        value = portNumber,
                        onValueChange = { portNumber = it },
                        label = { Text("Port Number") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Confirmation action
                    }
                ) { Text("Update") }
            }
        )
    }
}
*/


