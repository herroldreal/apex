package com.apex.data.mappers.bo

import androidx.paging.PagingData
import androidx.paging.map
import com.apex.data.mappers.entity.toPagingBO
import com.apex.domain.models.CharacterBO
import com.apex.localsource.entitites.CharacterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

fun Flow<PagingData<CharacterEntity>>.toPagingBO(): Flow<PagingData<CharacterBO>> {
    return transform { value ->
        emit(value.map {
            it.toPagingBO()
        })
    }
}

fun Flow<CharacterEntity>.toBO() : Flow<CharacterBO> {
    return transform { value ->
        emit(value.toPagingBO())
    }
}