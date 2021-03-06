package com.sundaydavid989.shesmakeup.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val makeUpId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)