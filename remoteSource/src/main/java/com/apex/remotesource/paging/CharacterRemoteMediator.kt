package com.apex.remotesource.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.apex.localsource.daos.CharacterDao
import com.apex.localsource.entitites.CharacterEntity
import com.apex.remotesource.api.APIService
import com.apex.remotesource.mapper.toEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val apiService: APIService,
    private val characterDao: CharacterDao,
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return  MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) 1
                    else (lastItem.characterId!! / state.config.pageSize) + 1
                }
            }

            Log.e("RemoteMediator => ", "Page => ${page}")
            val response = apiService.getCharacters(page)
            if (response.isSuccessful) {
                val characters = response.body()
                characters?.let {
                    if (loadType == LoadType.REFRESH) {
                        characterDao.deleteAllCharacters()
                    }

                    it.results
                        ?.map { it.toEntity() }
                        ?.map { entity -> characterDao.addCharacters(entity) }
                }
                MediatorResult.Success(endOfPaginationReached = characters?.info?.pages == page)
            } else {
                MediatorResult.Error(Throwable("Error loading items from remote source"))
            }
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
