package com.apex.remotesource.mapper

import com.apex.localsource.entitites.CharacterEntity
import com.apex.remotesource.dto.CharacterDto

fun CharacterDto.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = id ?: 0,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        image = image,
        url = url,
        created = created,
    )
}