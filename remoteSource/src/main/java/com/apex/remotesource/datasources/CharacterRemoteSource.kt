package com.apex.remotesource.datasources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apex.localsource.daos.CharacterDao
import com.apex.localsource.entitites.CharacterEntity
import com.apex.remotesource.api.APIService
import com.apex.remotesource.paging.CharacterRemoteMediator
import io.realm.kotlin.Realm
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteSource(
    private val apiService: APIService,
    private val characterDao: CharacterDao,
    private val realm: Realm
) {
    fun getCharacters(): Flow<PagingData<CharacterEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 3,
                enablePlaceholders = false
            ),
            remoteMediator = CharacterRemoteMediator(apiService, characterDao),
            pagingSourceFactory = { characterDao.getAllCharacters() }
        ).flow
    }
}
