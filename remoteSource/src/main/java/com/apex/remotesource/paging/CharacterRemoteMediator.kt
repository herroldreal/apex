package com.apex.remotesource.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.apex.localsource.AppDatabase
import com.apex.localsource.entitites.CharacterEntity
import com.apex.remotesource.api.APIService
import com.apex.remotesource.mapper.toEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val apiService: APIService,
    private val db: AppDatabase,
) : RemoteMediator<Int, CharacterEntity>() {

    private val characterDao = db.characterDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>,
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) 1
                    else (lastItem.id / state.config.pageSize) + 1
                }
            }

            val response = apiService.getCharacters(page)
            if (response.isSuccessful) {
                val characters = response.body()
                characters.let {
                    db.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            characterDao.deleteAllCharacters()
                        }

                        characters?.results
                            ?.map { it.toEntity() }
                            ?.let {
                                characterDao.addCharacters(it)
                            }
                    }
                }
            }

            MediatorResult.Success(endOfPaginationReached = response.body()?.info?.next == null)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}

/*override fun getRefreshKey(state: PagingState<Int, CharacterEntity>) = null

override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterEntity> {
    val pageNumber = params.key ?: 1
    val characterDao = db.characterDao()

    return try {
        val response =
            apiService.getCharacters(page = pageNumber)

        val data = response.body()?.results?.map { it.toEntity() }

        db.withTransaction {
            if (params.key == null) {
                characterDao.deleteAllCharacters()
            }

            data?.let {
                characterDao.addCharacters(it)
            }
        }

        var nextPageNumber: Int? = null

        if (response.body()?.info?.next != null) {
            val uri = Uri.parse(response.body()?.info?.next)
            val nextPageQuery = uri.getQueryParameter("page")
            nextPageNumber = nextPageQuery?.toInt()
        }

        LoadResult.Page(
            data = data!!,
            prevKey = if (pageNumber == 1) null else pageNumber - 1,
            nextKey = nextPageNumber
        )

    } catch (e: Exception) {
        LoadResult.Error(e)
    }
}

} */