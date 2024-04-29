package com.example.projectlpg.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sensor_data")
data class SensorDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val deviceId: String,
    val weight: String? = "30.0",
    val timestamp: Long? = System.currentTimeMillis()
)
