package com.sundaydavid989.shesmakeup.data.network

import com.google.gson.annotations.SerializedName
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem


data class MakeupResponse(
    @SerializedName("items") val items: Array<MakeupItem> = emptyArray()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MakeupResponse

        if (!items.contentEquals(other.items)) return false

        return true
    }

    override fun hashCode(): Int {
        return items.contentHashCode()
    }

}