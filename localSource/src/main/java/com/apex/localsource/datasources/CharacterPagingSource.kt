package com.apex.localsource.datasources;

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apex.localsource.entitites.CharacterEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort

class CharacterPagingSource(
    private val realm: Realm
) : PagingSource<Int, CharacterEntity>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterEntity> {
        return try {
            val page = params.key ?: 0
            val pageSize = params.loadSize

            val query = realm.query<CharacterEntity>()
                .sort("name", Sort.ASCENDING)
                .distinct("characterId")
                .find()
            val paginatedCharacters = query.drop(page * pageSize).take(pageSize)

            LoadResult.Page(
                data = paginatedCharacters,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (paginatedCharacters.size < pageSize) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
