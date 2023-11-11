package com.apex.localsource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.apex.localsource.converters.ListConverters
import com.apex.localsource.daos.CharacterDao
import com.apex.localsource.entitites.CharacterEntity

@Database(
    entities = [CharacterEntity::class],
    version = 3,
)
@TypeConverters(ListConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}