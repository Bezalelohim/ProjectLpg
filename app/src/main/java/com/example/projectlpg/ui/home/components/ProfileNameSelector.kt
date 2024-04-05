package com.example.projectlpg.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.projectlpg.ui.home.HomeScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileNameDropDown(viewModel:HomeScreenViewModel = hiltViewModel()) {
    val profileNames by viewModel.profileNames.observeAsState(initial = emptyList())
    var selectedProfileName by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            TextField(
                readOnly = true,
                value = selectedProfileName,
                onValueChange = { /* No op */ },
                label = { Text("Select Profile Name") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier.fillMaxWidth().clickable { expanded = !expanded }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                profileNames.forEach { profileName ->
                    DropdownMenuItem(
                       text = { Text(profileName)
                        },
                        onClick = {
                            selectedProfileName = profileName
                            expanded = false
                            // Use ViewModel to get deviceId by profileName
                            val deviceId = viewModel.getDeviceIdByProfileName(profileName)
                            // TODO: Do something with deviceId
                        }
                    )
                }
            }
        }
    }
}