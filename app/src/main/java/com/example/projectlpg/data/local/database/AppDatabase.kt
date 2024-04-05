package com.example.projectlpg.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.projectlpg.data.local.dao.AppDao
import com.example.projectlpg.data.local.entity.DeviceInfoEntity
import com.example.projectlpg.data.local.entity.SensorDataEntity

@Database(entities = [SensorDataEntity::class, DeviceInfoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}
