package com.example.projectlpg.ui.devices

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.projectlpg.data.local.entity.DeviceInfoEntity
import com.example.projectlpg.data.models.DeviceInfo
import com.example.projectlpg.data.repository.AppRepository
import com.example.projectlpg.network.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.S)
@HiltViewModel
class DevicesScreenViewModel @Inject constructor(
    private val networkManager: NetworkManager,
    private val appRepository: AppRepository
) : ViewModel() {
    val allDeviceInfo: LiveData<List<DeviceInfoEntity>> = appRepository.allDeviceInfo
    @RequiresApi(Build.VERSION_CODES.S)
    val wifiConnectedStatus = networkManager.wifiConnected.asLiveData()

    // LiveData to trigger the configuration dialog
    private val _showConfigureDialog = MutableStateFlow(false)
    val showConfigureDialog = _showConfigureDialog.asStateFlow()

    private val _showErrorDialog = MutableStateFlow(false)
    val showErrorDialog = _showErrorDialog.asStateFlow()

    private val _showProfileNameDialog = MutableStateFlow(false)
    val showProfileNameDialog = _showProfileNameDialog.asStateFlow()

    val existingProfileNames: LiveData<List<String>> = appRepository.getDistinctProfileNames().asLiveData()

    private var tempSSID = ""

    init {
        // Collect from newSSIDSharedFlow
        viewModelScope.launch {
            networkManager.newSSIDSharedFlow.collect { ssid ->
                tempSSID = ssid
                Log.d("SSIDMADE","$tempSSID ViewModel")
                _showProfileNameDialog.value = true // Trigger UI to show dialog

            }
        }
    }


    // Function to update device configuration
    fun updateDeviceConfiguration(profileName: String, ipAddress: String, portNumber: Int) {
        viewModelScope.launch {
            if (validateIpAddress(ipAddress) && validatePortNumber(portNumber) && uniqueProfileName(profileName)) {
                // Assuming you have a method in your repository to update device info
                appRepository.updateDeviceDetails(profileName, ipAddress, portNumber)
                // Hide configuration dialog
                _showConfigureDialog.value = false
            } else {
                _showErrorDialog.value = true
            }
        }
    }

    fun saveDeviceWithProfileName(profileName: String) {
        viewModelScope.launch {
            if (profileName.isNotEmpty()) {
                val deviceId = UUID.randomUUID().toString()
                appRepository.insertDeviceInfo(
                    DeviceInfo(
                        deviceId = deviceId,
                        ssid = tempSSID,
                        profileName = profileName,
                        ipAddress = "192.168.1.127", // default value or empty if not known
                        port = 80 // default value or 0 if not known
                    )
                )
                tempSSID = "" // Reset temporary SSID
                _showProfileNameDialog.value = false // Close the dialog
            }
        }
    }


    fun showConfigurationDialog() {
        _showConfigureDialog.value = true
    }
    fun closeConfigurationDialog(){
        _showConfigureDialog.value = false
    }

    fun dismissErrorDialog() {
        _showErrorDialog.value = false
    }

    private suspend fun uniqueProfileName(profileName: String) : Boolean
    {
        return appRepository.isProfileNameExists(profileName)
    }
    private fun validateIpAddress(ipAddress: String): Boolean {
        // Simple regex pattern for IP address validation
        return ipAddress.matches(Regex("\\\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\\\b"))
    }

    private fun validatePortNumber(portNumber: Int): Boolean {
        return portNumber in 1..65535
    }
}
    // rest of the ViewModel...
