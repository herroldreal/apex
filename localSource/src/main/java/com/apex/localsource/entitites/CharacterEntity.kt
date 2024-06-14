package com.apex.localsource.entitites

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class CharacterEntity : RealmObject {
    @PrimaryKey
    var id: Int? = null
    var name: String? = null
    var status: String? = null
    var species: String? = null
    var type: String? = null
    var gender: String? = null
    var image: String? = null
    var url: String? = null
    var created: String? = null
}