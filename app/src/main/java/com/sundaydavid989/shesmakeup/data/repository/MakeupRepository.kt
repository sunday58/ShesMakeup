package com.sundaydavid989.shesmakeup.data.repository

import androidx.paging.PagingData
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import kotlinx.coroutines.flow.Flow

interface MakeupRepository {
    fun getMakeupResultStream(): Flow<PagingData<MakeupItem>>
}