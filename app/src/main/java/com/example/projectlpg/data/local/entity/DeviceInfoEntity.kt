package com.example.projectlpg.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "device_info")
data class DeviceInfoEntity(
    @PrimaryKey val deviceId: String,
    val ssid: String,
    var profileName: String? = null,
    var ipAddress: String,
    var port: Int
)

