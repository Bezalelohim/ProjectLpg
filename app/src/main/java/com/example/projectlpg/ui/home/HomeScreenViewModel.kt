package com.example.projectlpg.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.projectlpg.data.repository.AppRepository
import com.example.projectlpg.network.DataTransferManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val appRepository: AppRepository,
                                              private val dataTransferManager: DataTransferManager
) : ViewModel() {

    val profileNames:LiveData<List<String>> = appRepository.getDistinctProfileNames().asLiveData()
    private val profileNameToDeviceIdMap = mutableStateOf<Map<String, String>>(emptyMap())
    private val _selectedDeviceId = MutableStateFlow<String?>(null)
    private val _sensorWeightStateFlow = MutableStateFlow(0.30f)
    val sensorWeightStateFlow: StateFlow<Float> = _sensorWeightStateFlow.asStateFlow()


    // val selectedDeviceId: StateFlow<String?> = _selectedDeviceId.asStateFlow()

    init {
        viewModelScope.launch {
            appRepository.profileNamesWithDeviceIds.collect {
                profileNameToDeviceIdMap.value = it
            }
        }
        getWeightByDevice(_selectedDeviceId.value)
    }

    fun getDeviceIdByProfileName(profileName: String): String? {
        return profileNameToDeviceIdMap.value[profileName]
    }

    fun setSelectedDeviceId(deviceId: String?) {
        _selectedDeviceId.value = deviceId
    }

    fun getWeightByDevice(selectedDevice : String?) {
        if (selectedDevice != null) {
            // Assume repository.getSensorDataFlow() returns Flow<SensorDataEntity>
            val sensorDataFlow = appRepository.getLatestSensorData(selectedDevice)

            // Transform the SensorDataEntity flow to a Float flow representing the weight
            val sensorWeightFlow = sensorDataFlow.map { sensorData ->
                sensorData.weight?.toFloatOrNull() ?: 0.0f
            }

            // Collect sensorWeightFlow in a coroutine and update _sensorWeightStateFlow
            viewModelScope.launch {
                sensorWeightFlow.collect { weight ->
                    _sensorWeightStateFlow.value = weight
                }
            }
        }
    }

    // Function to sync data for the selected device
    @RequiresApi(Build.VERSION_CODES.S)
    fun syncDataForSelectedDevice(sendData: String = "WIFI_CONNECTED") {
        val deviceId = _selectedDeviceId.value ?: return
        viewModelScope.launch {
            appRepository.getDeviceById(deviceId)?.let { device ->
                dataTransferManager.sendAndReceiveData(device.ipAddress, device.port, sendData)
                }
            }
        }
    }



