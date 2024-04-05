package com.example.projectlpg.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.projectlpg.data.repository.AppRepository
import com.example.projectlpg.network.DataTransferManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val appRepository: AppRepository,
                                              private val dataTransferManager: DataTransferManager
) : ViewModel() {

    val profileNames: LiveData<List<String>> = appRepository.getDistinctProfileNames().asLiveData()
    private val profileNameToDeviceIdMap = mutableStateOf<Map<String, String>>(emptyMap())
    private val _selectedDeviceId = MutableStateFlow<String?>(null)
    val selectedDeviceId: StateFlow<String?> = _selectedDeviceId.asStateFlow()

    init {
        viewModelScope.launch {
            appRepository.profileNamesWithDeviceIds.collect {
                profileNameToDeviceIdMap.value = it
            }
        }
    }

    fun getDeviceIdByProfileName(profileName: String): String? {
        return profileNameToDeviceIdMap.value[profileName]
    }

    fun setSelectedDeviceId(deviceId: String) {
        _selectedDeviceId.value = deviceId
    }

    // Function to sync data for the selected device
    fun syncDataForSelectedDevice(sendData: String = "WIFI_CONNECTED") {
        val deviceId = _selectedDeviceId.value ?: return
        viewModelScope.launch {
            appRepository.getDeviceById(deviceId)?.let { device ->
                dataTransferManager.sendAndReceiveData(device.ipAddress, device.port, sendData)?.let { sensorData ->
                    // Process received sensor data
                }
            }
        }
    }
}


