package com.apex.localsource.entitites

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RemoteKeysEntity : RealmObject {
    @PrimaryKey
    var repoId: Int? = null
    var prevKey: Int? = null
    var nextKey: Int? = null
}