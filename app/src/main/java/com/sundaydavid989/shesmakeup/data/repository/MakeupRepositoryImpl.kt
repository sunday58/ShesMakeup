package com.sundaydavid989.shesmakeup.data.repository

import androidx.lifecycle.LiveData
import com.sundaydavid989.shesmakeup.data.db.entity.Makeup

class MakeupRepositoryImpl : MakeupRepository {
    override suspend fun getMakeup(): LiveData<Makeup> {
        TODO("Not yet implemented")
    }
}