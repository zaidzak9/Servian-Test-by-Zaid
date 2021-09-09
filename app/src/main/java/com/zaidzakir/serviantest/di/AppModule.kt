package com.zaidzakir.serviantest.di

import android.content.Context
import androidx.room.Room
import com.zaidzakir.serviantest.data.localDB.ServianDatabase
import com.zaidzakir.serviantest.data.remote.UserApi
import com.zaidzakir.serviantest.util.Constants.BASE_URL
import com.zaidzakir.serviantest.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 *Created by Zaid Zakir
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideUserInfoApi(): UserApi {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(UserApi::class.java)
    }
    @Singleton
    @Provides
    fun provideUserDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ServianDatabase::class.java, DATABASE_NAME).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideUserDao(
        database: ServianDatabase
    ) = database.getUserDao()
}