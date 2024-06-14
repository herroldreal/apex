package com.apex.localsource.daos;

import com.apex.localsource.entitites.RemoteKeysEntity;
import io.realm.kotlin.Realm;
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort

class RemoteKeysDao(private val realm: Realm) {

    suspend fun insert(remoteKey: RemoteKeysEntity) {
        realm.write {
            copyToRealm(remoteKey, UpdatePolicy.ALL)
        }
    }

    fun remoteKeysRepoId(repoId: Long): RemoteKeysEntity? {
        return realm.query<RemoteKeysEntity>("repoId == $0", repoId)
            .find().firstOrNull()
    }

    suspend fun deleteAllRemoteKeys() {
        realm.write {
            val keys = query<RemoteKeysEntity>().find()
            delete(keys)
        }
    }

    fun getLastRemoteKey(): RemoteKeysEntity? {
        return realm.query<RemoteKeysEntity>()
            .sort("repoId", Sort.DESCENDING)
            .limit(1)
            .find()
            .firstOrNull()
    }
}