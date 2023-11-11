package com.apex.remotesource.datasources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apex.localsource.AppDatabase
import com.apex.localsource.entitites.CharacterEntity
import com.apex.remotesource.api.APIService
import com.apex.remotesource.paging.CharacterRemoteMediator
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteSource(
    private val apiService: APIService,
    private val db: AppDatabase,
) {

    private val characterDao = db.characterDao()

    fun getCharacters(): Flow<PagingData<CharacterEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = CharacterRemoteMediator(apiService, db),
        ) {
            characterDao.getAllCharacters()
        }.flow
    }
}
