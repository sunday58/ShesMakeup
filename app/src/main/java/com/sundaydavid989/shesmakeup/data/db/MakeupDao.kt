package com.sundaydavid989.shesmakeup.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sundaydavid989.shesmakeup.data.db.entity.Makeup
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem

@Dao
interface MakeupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(makeupItem: Makeup)

    @Query("SELECT * FROM make_up")
    fun getMakeup(): LiveData<List<MakeupItem>>
}