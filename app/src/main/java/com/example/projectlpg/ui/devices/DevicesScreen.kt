package com.example.projectlpg.ui.devices

import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.projectlpg.data.local.entity.DeviceInfoEntity
import com.example.projectlpg.ui.devices.components.ConfigureDeviceDialog
import com.example.projectlpg.ui.devices.components.ErrorDialog
import com.example.projectlpg.ui.devices.components.ProfileNameInputDialog


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun DevicesScreen(viewModel: DevicesScreenViewModel = hiltViewModel()) {

    // Use viewModel
    val devices by viewModel.allDeviceInfo.observeAsState(initial = emptyList())
    val wifiConnected by viewModel.wifiConnectedStatus.observeAsState(initial = false)
    val context = LocalContext.current
    val showProfileNameDialog by viewModel.showProfileNameDialog.collectAsState()
    val showConfigureDialog by viewModel.showConfigureDialog.collectAsState()
    val showErrorDialog by viewModel.showErrorDialog.collectAsState()

    // This shows the dialog based on the `showProfileNameDialog` state
    if (showProfileNameDialog) {
        ProfileNameInputDialog(viewModel)
    }

    if (showConfigureDialog) {
        ConfigureDeviceDialog(viewModel)
    }

    if(showErrorDialog){
        ErrorDialog(viewModel)
    }

    Scaffold ()
 { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            DeviceListHeader()
            DevicesList(devices, wifiConnected)
            DeviceControlButtons(onScanClicked = { context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS)) }, onConfigureClicked = { viewModel.showConfigurationDialog() } )

        }
    }
}


@Composable
fun DeviceListHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Profile", modifier = Modifier
            .weight(1f)
            .padding(start = 8.dp))
        Text("SSID", modifier = Modifier.weight(1f))
        Text("STATUS", modifier = Modifier
            .weight(1f)
            .padding(end = 8.dp))
    }
}

@Composable
fun DevicesList(devices: List<DeviceInfoEntity>, wifiConnected: Boolean) {
    LazyColumn {
        items(devices) { device ->
            DeviceItemCard(device = device, wifiConnected = wifiConnected)
        }
    }
}

@Composable
fun DeviceItemCard(device: DeviceInfoEntity, wifiConnected: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            device.profileName?.let { Text(it, modifier = Modifier.weight(1f)) }
            Text(device.ssid, modifier = Modifier.weight(1f))
            Text(if (wifiConnected) "READY" else "NOT READY", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun DeviceControlButtons(onScanClicked: () -> Unit, onConfigureClicked: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = onScanClicked, modifier = Modifier.padding(bottom = 8.dp)) {
            Text("SCAN")
        }
        Button(onClick = onConfigureClicked) {
            Text("CONFIGURE")
        }
    }
}

/*
@Composable
fun DevicesList(devices: List<DeviceInfoEntity>, wifiConnected: Boolean) {
    LazyColumn {
        items(devices) { device ->
            DeviceItem(device = device, wifiConnected = wifiConnected)
        }
    }
}

@Composable
fun DeviceItem(device: DeviceInfoEntity, wifiConnected: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        device.profileName?.let { Text(it, modifier = Modifier.weight(1f)) }
        Text(device.ssid, modifier = Modifier.weight(1f))
        Text(if (wifiConnected) "READY" else "NOT READY", modifier = Modifier.weight(1f))
    }
}

@Composable
fun DeviceControlButtons(onScanClicked: () -> Unit, onConfigureClicked: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(onClick = onScanClicked) {
            Text("SCAN")
        }
        Button(onClick = onConfigureClicked) {
            Text("CONFIGURE")
        }
    }
}
*/