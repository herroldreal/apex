package com.apex.localsource.extensions

import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T : RealmObject> RealmResults<T>.toFlow(): Flow<List<T>> {
    return this.asFlow().map { it.list }
}

fun <T : RealmObject> RealmResults<T>.toSingleFlow(): Flow<T?> {
    return this.asFlow().map { it.list.firstOrNull() }
}