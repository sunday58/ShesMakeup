package com.sundaydavid989.shesmakeup.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem

@Dao
interface MakeupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(makeupItem: Array<MakeupItem>)

    @Query("SELECT * FROM make_up")
    fun getMakeup(): PagingSource<Int, MakeupItem>

    @Query("DELETE FROM make_up")
    suspend fun clearMakeups()
}