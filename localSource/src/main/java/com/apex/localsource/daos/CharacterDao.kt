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
    suspend fun insertAll(character: List<CharacterEntity>) {
        realm.write {
            character.forEach {
                copyToRealm(it, UpdatePolicy.ALL)
            }
        }
    }

    fun getAllCharacters(): PagingSource<Int, CharacterEntity> {
        return CharacterPagingSource(realm)
    }

    fun getCharacter(characterId: Int?): Flow<CharacterEntity?> {
        return realm.query<CharacterEntity>("id == $0", characterId)
            .find().toSingleFlow()
    }

    suspend fun deleteAllCharacters() {
        realm.write {
            val characters = query<CharacterEntity>().find()
            delete(characters)
        }
    }
}
