package com.example.projectlpg.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "device_info")
data class ProfileNameAndDeviceIdEntity(
    val profileName: String,
    @PrimaryKey val deviceId: String,
)
