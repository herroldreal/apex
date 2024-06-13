package com.apex.localsource.daos

import androidx.paging.PagingSource
import com.apex.localsource.datasources.CharacterPagingSource
import com.apex.localsource.entitites.CharacterEntity
import com.apex.localsource.extensions.toSingleFlow
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow

class CharacterDao(
    private val realm: Realm
) {
    suspend fun addCharacters(character: CharacterEntity) {
        realm.write {
            copyToRealm(character, UpdatePolicy.ALL)
        }
    }

    fun getAllCharacters(): PagingSource<Int, CharacterEntity> {
        return CharacterPagingSource(realm)
    }

    fun getCharacter(characterId: Int?): Flow<CharacterEntity?> {
        val query = realm.query<CharacterEntity>("characterId == $0", characterId)
            .find()

        return query.toSingleFlow()
    }

    suspend fun deleteAllCharacters() {
        realm.write {
            val characters = query<CharacterEntity>().find()
            delete(characters)
        }
    }
}
