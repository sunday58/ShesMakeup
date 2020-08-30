package com.sundaydavid989.shesmakeup.data.network

import androidx.lifecycle.LiveData
import com.sundaydavid989.shesmakeup.data.db.entity.Makeup

interface MakeupNetworkDataSource {
    val downloadMakeup: LiveData<Makeup>

    suspend fun fetchMakeup()
}