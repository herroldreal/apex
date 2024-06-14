package com.apex.remotesource.mapper

import com.apex.localsource.entitites.CharacterEntity
import com.apex.remotesource.dto.CharacterDto

fun CharacterDto.toEntity(): CharacterEntity {
    val entity = CharacterEntity()
    entity.id = id ?: 0
    entity.name = this.name
    entity.status = this.status
    entity.species = this.species
    entity.type = this.type
    entity.gender = this.gender
    entity.image = this.image
    entity.url = this.url
    entity.created = this.created

    return entity
}