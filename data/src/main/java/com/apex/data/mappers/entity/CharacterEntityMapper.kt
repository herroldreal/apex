package com.apex.data.mappers.entity

import com.apex.domain.models.CharacterBO
import com.apex.localsource.entitites.CharacterEntity

fun CharacterEntity.toPagingBO(): CharacterBO {
    return CharacterBO(
        id,
        name,
        status,
        species,
        type,
        gender,
        image,
        url,
        created
    )
}