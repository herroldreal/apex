package com.apex.localsource.di

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.apex.localsource.AppDatabase
import com.apex.localsource.Constants
import com.apex.localsource.datasources.CharacterLocalSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@VisibleForTesting
val DATABASE_NAME = Constants.DB_NAME

fun provideDatabase(appContext: Context): AppDatabase =
    Room.databaseBuilder(
        appContext,
        AppDatabase::class.java,
        DATABASE_NAME,
    ).fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .addMigrations()
        .build()

val localSourceModule = module {
    single { provideDatabase(androidContext()) }
    single { CharacterLocalSource(get()) }
}
