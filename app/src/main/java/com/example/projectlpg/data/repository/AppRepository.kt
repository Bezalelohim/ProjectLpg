package com.example.projectlpg.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.projectlpg.data.local.dao.AppDao
import com.example.projectlpg.data.local.entity.DeviceInfoEntity
import com.example.projectlpg.data.local.entity.SensorDataEntity
import com.example.projectlpg.data.models.DeviceInfo
import com.example.projectlpg.data.models.SensorData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(private val appDao: AppDao) {

    // Get all devices information
    val allDeviceInfo: LiveData<List<DeviceInfoEntity>> = appDao.getAllDeviceInfo()

    val profileNamesWithDeviceIds: Flow<Map<String, String>> = appDao.getProfileNamesAndDeviceIds()
        .map { list -> list.associate { it.profileName to it.deviceId } }


    // Insert or update a device information
    suspend fun insertDeviceInfo(deviceInfo: DeviceInfo) {
        val deviceInfoEntity = DeviceInfoEntity(
            deviceId = deviceInfo.deviceId,
            ssid = deviceInfo.ssid,
            profileName = deviceInfo.profileName,
            ipAddress = deviceInfo.ipAddress,
            port = deviceInfo.port
        )
        appDao.insertDeviceInfo(deviceInfoEntity)
    }

    // Update device information with user input for profile name, IP address, and port number
    suspend fun updateDeviceDetails(
        profileName: String,
        ipAddress: String,
        port: Int,

    ) {
        appDao.updateDeviceInfo( profileName, ipAddress, port)
    }

    // Get the latest sensor data for a device
     fun getLatestSensorData(deviceId: String?): Flow<SensorDataEntity> {
        return appDao.getLatestSensorData(deviceId)
    }

    // Convert the LiveData logic to StateFlow

    // Insert new sensor data
    suspend fun insertSensorData(sensorData: SensorData) {
        val sensorDataEntity = SensorDataEntity(
            deviceId = sensorData.deviceId,
            weight = sensorData.weight,
            timestamp  =  sensorData.timestamp
        )
        appDao.insertSensorData(sensorDataEntity)
    }

     suspend fun isProfileNameExists(profileName: String): Boolean {
        return appDao.existsDeviceInfoByProfileName(profileName)
    }

    suspend fun deviceKnownSSID(ssid: String):Boolean  {
        return appDao.isNetworkKnown(ssid)

    }
    suspend fun getDeviceIdBySSID(ssid: String): String{
        return appDao.getDeviceIdBySSID(ssid)
    }
    suspend fun getDeviceById(deviceId: String) : DeviceInfoEntity?
    {
        return appDao.getDeviceById(deviceId)
    }

    fun getDistinctProfileNames(): Flow<List<String>> = appDao.getDistinctProfileNames()
}