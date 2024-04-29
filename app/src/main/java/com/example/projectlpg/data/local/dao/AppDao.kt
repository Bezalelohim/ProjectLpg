package com.example.projectlpg.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.projectlpg.data.local.entity.DeviceInfoEntity
import com.example.projectlpg.data.local.entity.SensorDataEntity
import com.example.projectlpg.data.models.ProfileNameAndDeviceId
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // Device Info Operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeviceInfo(deviceInfoEntity: DeviceInfoEntity)

    @Query("SELECT * FROM device_info")
    fun getAllDeviceInfo(): LiveData<List<DeviceInfoEntity>>

    // Sensor Data Operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSensorData(sensorDataEntity: SensorDataEntity)

    @Query("SELECT * FROM sensor_data WHERE deviceId = :deviceId ORDER BY timestamp DESC LIMIT 1")
    fun getLatestSensorData(deviceId: String?): Flow<SensorDataEntity>


    @Query("SELECT EXISTS(SELECT * FROM device_info WHERE ssid = :ssid)")
    suspend fun isNetworkKnown(ssid: String): Boolean


    @Query("UPDATE device_info SET  ipAddress = :ipAddress, port = :port WHERE profileName = :profileName")
    suspend fun updateDeviceInfo(
        profileName: String,
        ipAddress: String,
        port: Int
    )

    @Query("SELECT deviceId FROM device_info WHERE ssid = :ssid LIMIT 1")
    suspend fun getDeviceIdBySSID(ssid: String): String

    @Query("SELECT DISTINCT profileName FROM device_info")
    fun getDistinctProfileNames(): Flow<List<String>>

    @Query("SELECT profileName, deviceId FROM device_info")
    fun getProfileNamesAndDeviceIds(): Flow<List<ProfileNameAndDeviceId>>

    @Query("SELECT EXISTS(SELECT 1 FROM device_info WHERE profileName = :profileName)")
    suspend fun existsDeviceInfoByProfileName(profileName: String): Boolean

    @Query("SELECT * FROM device_info WHERE deviceId = :deviceId LIMIT 1")
    suspend fun getDeviceById(deviceId: String): DeviceInfoEntity?

}