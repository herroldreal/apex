package com.apex.remotesource.mapper

import com.apex.localsource.entitites.CharacterEntity
import com.apex.remotesource.dto.CharacterDto
import org.mongodb.kbson.ObjectId

fun CharacterDto.toEntity(): CharacterEntity {
    val entity = CharacterEntity()
    entity._id = ObjectId()
    entity.characterId = id ?: 0
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