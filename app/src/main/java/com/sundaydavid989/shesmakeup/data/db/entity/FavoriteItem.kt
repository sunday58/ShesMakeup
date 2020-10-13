package com.sundaydavid989.shesmakeup.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "favorite_table")
data class FavoriteItem(
    @PrimaryKey(autoGenerate = false)
    val favId : Int,
    @Embedded
    val makeup: MakeupItem,
    val isFavorite: Boolean
): Serializable