package com.apex.localsource.di

import androidx.annotation.VisibleForTesting
import com.apex.localsource.Constants
import com.apex.localsource.daos.CharacterDao
import com.apex.localsource.datasources.CharacterLocalSource
import com.apex.localsource.entitites.CharacterEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

@VisibleForTesting
val DATABASE_NAME = Constants.DB_NAME

fun provideDatabase(): Realm {
    val schemas = setOf(
        CharacterEntity::class
    )
    val config = RealmConfiguration.Builder(schemas)
        .name("apex.realm")
        .deleteRealmIfMigrationNeeded()
        .build()

    return Realm.open(config)
}

val localSourceModule = module {
    single { provideDatabase() }
    single { CharacterLocalSource(get()) }
    single { CharacterDao(get()) }
}
