package com.sundaydavid989.shesmakeup.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite")
data class FavoriteItem(
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val makeup: MakeupItem,
    val isFavorite: Boolean
)