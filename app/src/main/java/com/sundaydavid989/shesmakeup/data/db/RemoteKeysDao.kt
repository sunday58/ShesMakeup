package com.sundaydavid989.shesmakeup.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sundaydavid989.shesmakeup.data.db.entity.RemoteKeys


@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE makeUpId = :makeUpId")
    suspend fun remoteKeysMakeUpId(makeUpId: Int): RemoteKeys

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}