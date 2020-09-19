package com.sundaydavid989.shesmakeup.data.repository

import androidx.lifecycle.LiveData
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem

interface MakeupRepository {
    suspend fun getMakeup(): LiveData<List<MakeupItem>>
    suspend fun getProductType(): LiveData<List<MakeupItem>>

    suspend fun fetchProductType(name: String)
}