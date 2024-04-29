package com.example.projectlpg.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projectlpg.ui.home.components.GasCylinder
import com.example.projectlpg.ui.home.components.LocationPermissionDialog
import com.example.projectlpg.ui.home.components.PreviewProfileNameDropDown
import com.example.projectlpg.ui.home.components.PreviewSyncButton
import com.example.projectlpg.ui.home.components.ProfileNameDropDown
import com.example.projectlpg.ui.home.components.SyncButton
import com.example.projectlpg.ui.navigation.BottomNavigationBar

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun HomeScreen(viewModel: HomeScreenViewModel) {

    val latestWeight by viewModel.sensorWeightStateFlow.collectAsState()

    Scaffold()
    { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top
            ) {

                SyncButton(viewModel)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                ProfileNameDropDown(viewModel)
            }



            LocationPermissionDialog(onPermissionGranted = {
                // Actions to perform once location permission is granted
            })

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center // This will center the content inside the Box
            ) {
                GasCylinder(
                    progress = latestWeight, // % value between 0.0f and 1.0f
                    modifier = Modifier
                        .width(200.dp)
                        .height(500.dp)
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    // Since it's a preview, let's use a mocked latestWeight value
    val latestWeight = 0.25f // Mocked sensor weight value for preview

    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Row(
                modifier = Modifier
                    .padding(top = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top
            ) {
                PreviewSyncButton()
                PreviewProfileNameDropDown(profileNames = listOf("Profile 1", "Profile 2", "Profile 3"), // Sample data for preview
                    onProfileSelected = { /* Handle selection in real app */ })

            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center // This will center the content inside the Box
            ) {
                GasCylinder(
                    progress = latestWeight,
                    modifier = Modifier
                        .width(200.dp)
                        .height(500.dp)
                )
            }
        }
    }
}