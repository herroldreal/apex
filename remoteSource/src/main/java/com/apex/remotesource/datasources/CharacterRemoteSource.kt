package com.apex.remotesource.datasources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apex.localsource.daos.CharacterDao
import com.apex.localsource.daos.RemoteKeysDao
import com.apex.localsource.entitites.CharacterEntity
import com.apex.remotesource.api.APIService
import com.apex.remotesource.paging.CharacterRemoteMediator
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteSource(
    private val apiService: APIService,
    private val characterDao: CharacterDao,
    private val remoteKeyDao: RemoteKeysDao
) {
    companion object {
        private const val NETWORK_PAGE_SIZE_CHARACTER = 20
    }

    fun getCharacters(): Flow<PagingData<CharacterEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE_CHARACTER,
                initialLoadSize = NETWORK_PAGE_SIZE_CHARACTER,
                prefetchDistance = 3,
                enablePlaceholders = false,
            ),
            initialKey = 1,
            remoteMediator = CharacterRemoteMediator(apiService, characterDao, remoteKeyDao),
            pagingSourceFactory = { characterDao.getAllCharacters() }
        ).flow
    }
}
