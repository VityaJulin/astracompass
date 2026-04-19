package com.vityajulin.astracompass.di

import android.content.Context
import com.vityajulin.astracompass.data.CompassSensorManager
import com.vityajulin.astracompass.data.LevelSensorManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCompassSensorManager(@ApplicationContext context: Context): CompassSensorManager {
        return CompassSensorManager(context)
    }

    @Provides
    @Singleton
    fun provideLevelSensorManager(@ApplicationContext context: Context): LevelSensorManager {
        return LevelSensorManager(context)
    }
}