package com.apex.data.mappers.dto

import com.apex.domain.models.CharacterBO
import com.apex.remotesource.dto.CharacterDto

fun CharacterDto.toBO() : CharacterBO {
    return CharacterBO(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        image = image,
        url = url,
        created = created
    )
}