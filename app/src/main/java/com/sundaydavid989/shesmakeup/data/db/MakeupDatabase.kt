package com.sundaydavid989.shesmakeup.data.db

import androidx.room.Database
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem


@Database(
    exportSchema = false,
    entities = [MakeupItem::class],
    version = 1
)
abstract class MakeupDatabase {

}