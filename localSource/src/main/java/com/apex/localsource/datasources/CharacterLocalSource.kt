package com.apex.localsource.datasources

import com.apex.localsource.AppDatabase

class CharacterLocalSource(database: AppDatabase) {

    private val characterDao = database.characterDao()

    fun getCharacter(characterId: Int) = characterDao.getCharacter(characterId)
}