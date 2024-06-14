package com.apex.localsource.datasources

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
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterEntity> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize

            val data = realm.query<CharacterEntity>()
                .sort("id", Sort.ASCENDING)
                .find()
                .drop((page - 1) * pageSize)
                .take(pageSize)
                .toList()

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (data.isEmpty()) null else page + 1

            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
