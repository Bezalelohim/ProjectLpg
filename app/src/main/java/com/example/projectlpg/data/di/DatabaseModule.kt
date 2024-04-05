package com.example.projectlpg.data.di

import android.content.Context
import androidx.room.Room
import com.example.projectlpg.data.local.database.AppDatabase
import com.example.projectlpg.data.local.dao.AppDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Scope this module to the application's lifecycle
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        // Build the database instance
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "your_database_name.db"
        ).fallbackToDestructiveMigration() // Handle migrations properly in a real app
            .build()
    }

    @Provides
    fun provideAppDao(appDatabase: AppDatabase): AppDao {
        // Provide the DAO of the database
        return appDatabase.appDao()
    }

    // Add other DAO providers if necessary
}
