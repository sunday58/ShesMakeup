package com.sundaydavid989.shesmakeup.data.repository

import androidx.lifecycle.LiveData
import com.sundaydavid989.shesmakeup.data.db.entity.Makeup

interface MakeupRepository {
    suspend fun getMakeup(): LiveData<Makeup>
}