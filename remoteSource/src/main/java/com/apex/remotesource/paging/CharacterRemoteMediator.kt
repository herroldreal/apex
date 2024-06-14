package com.apex.remotesource.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.apex.localsource.daos.CharacterDao
import com.apex.localsource.daos.RemoteKeysDao
import com.apex.localsource.entitites.CharacterEntity
import com.apex.localsource.entitites.RemoteKeysEntity
import com.apex.remotesource.api.APIService
import com.apex.remotesource.mapper.toEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val apiService: APIService,
    private val characterDao: CharacterDao,
    private val remoteKeyDao: RemoteKeysDao
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    remoteKeys?.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
            }

            val response = apiService.getCharacters(page)
            if (response.isSuccessful) {
                val characters = response.body()?.results?.map { it.toEntity() } ?: emptyList()
                val endOfPaginationReached = characters.isEmpty()

                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.deleteAllRemoteKeys()
                    characterDao.deleteAllCharacters()
                }

                val lastCharacter = characters.last()
                val key = RemoteKeysEntity().apply {
                    repoId = lastCharacter.id
                    this.prevKey = prevKey
                    this.nextKey = nextKey
                }

                remoteKeyDao.insert(key)
                characterDao.insertAll(characters)

                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } else {
                MediatorResult.Error(Throwable("Error loading items from remote source"))
            }
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CharacterEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { character -> remoteKeyDao.remoteKeysRepoId(character.id!!.toLong()) }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CharacterEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { character -> remoteKeyDao.remoteKeysRepoId(character.id!!.toLong()) }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, CharacterEntity>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                remoteKeyDao.remoteKeysRepoId(repoId.toLong())
            }
        }
    }
}
