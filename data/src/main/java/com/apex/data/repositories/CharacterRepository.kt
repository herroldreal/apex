package com.apex.data.repositories

import androidx.paging.PagingData
import com.apex.data.mappers.bo.toBO
import com.apex.data.mappers.bo.toPagingBO
import com.apex.domain.models.CharacterBO
import com.apex.domain.repositories.ICharacterRepository
import com.apex.localsource.datasources.CharacterLocalSource
import com.apex.remotesource.datasources.CharacterRemoteSource
import kotlinx.coroutines.flow.Flow

class CharacterRepository(
    private val characterRemoteSource: CharacterRemoteSource,
    private val characterLocalSource: CharacterLocalSource,
) : ICharacterRepository {

    override suspend fun getCharacters(): Flow<PagingData<CharacterBO>> {
        return characterRemoteSource.getCharacters().toPagingBO()
    }

    override suspend fun characterDetail(id: Int?): Flow<CharacterBO> {
        return characterLocalSource.getCharacter(id).toBO()
    }
}
