package com.apex.localsource.datasources

import com.apex.localsource.daos.CharacterDao

class CharacterLocalSource(private val characterDao: CharacterDao) {

    fun getCharacter(characterId: Int?) = characterDao.getCharacter(characterId)
}