package com.apex.domain.repositories

import androidx.paging.PagingData
import com.apex.domain.models.CharacterBO
import kotlinx.coroutines.flow.Flow

interface ICharacterRepository {
    suspend fun getCharacters(): Flow<PagingData<CharacterBO>>
    suspend fun characterDetail(id: Int?): Flow<CharacterBO>
}