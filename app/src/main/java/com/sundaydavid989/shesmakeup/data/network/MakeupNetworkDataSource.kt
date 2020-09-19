package com.sundaydavid989.shesmakeup.data.network

import androidx.lifecycle.LiveData
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import com.sundaydavid989.shesmakeup.data.db.entity.ProductItem

interface MakeupNetworkDataSource {
    val downloadMakeup: LiveData<out Array<MakeupItem>>
    val downloadProductType: LiveData<out Array<ProductItem>>

    suspend fun fetchMakeup()
    suspend fun fetchProductType(name: String)
}