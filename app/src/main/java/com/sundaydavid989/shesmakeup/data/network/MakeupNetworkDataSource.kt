package com.sundaydavid989.shesmakeup.data.network

import androidx.lifecycle.LiveData
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem

interface MakeupNetworkDataSource {
    val downloadMakeup: LiveData<out Array<MakeupItem>>
    val downloadProductType: LiveData<out Array<MakeupItem>>

    suspend fun fetchMakeup()
    suspend fun fetchProductType(name: String)
}