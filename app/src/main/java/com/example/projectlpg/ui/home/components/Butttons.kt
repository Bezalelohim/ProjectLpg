package com.example.projectlpg.ui.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.projectlpg.ui.home.HomeScreenViewModel

@Composable
fun SyncButton(viewModel:HomeScreenViewModel = hiltViewModel()) {

    Button(
        onClick = { viewModel.syncDataForSelectedDevice() },
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Sync")
    }
}