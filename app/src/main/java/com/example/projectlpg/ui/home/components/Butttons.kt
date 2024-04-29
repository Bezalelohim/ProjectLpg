package com.example.projectlpg.ui.home.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.projectlpg.ui.home.HomeScreenViewModel

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun SyncButton(viewModel:HomeScreenViewModel = hiltViewModel()) {

    Button(
        onClick = { viewModel.syncDataForSelectedDevice() },
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Sync")
    }
}

@Composable
fun PreviewSyncButton()
{
    Button(
        onClick = {  },
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Sync")
    }
}