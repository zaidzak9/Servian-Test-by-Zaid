package com.zaidzakir.serviantest.di

import android.content.Context
import androidx.room.Room
import com.zaidzakir.serviantest.data.localDB.ServianDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

/**
 *Created by Zaid Zakir
 */

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("test_db")
    fun inMemoryDatabase(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context,ServianDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}