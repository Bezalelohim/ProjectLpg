package com.example.projectlpg.data.models

data class DeviceInfo(
    val deviceId: String,
    val ssid: String,
    var profileName : String?,
    var ipAddress: String,
    var port: Int
)
