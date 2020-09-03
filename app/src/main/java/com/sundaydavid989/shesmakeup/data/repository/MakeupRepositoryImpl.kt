package com.sundaydavid989.shesmakeup.data.repository

import androidx.lifecycle.LiveData
import com.sundaydavid989.shesmakeup.data.db.MakeupDao
import com.sundaydavid989.shesmakeup.data.db.entity.Makeup
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import com.sundaydavid989.shesmakeup.data.network.MakeupNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class MakeupRepositoryImpl(
    private val makeupDao: MakeupDao,
    private val makeupNetworkDataSource: MakeupNetworkDataSource
) : MakeupRepository {

    init {
        makeupNetworkDataSource.downloadMakeup.observeForever { makeup ->
            //persist makeups
            persistFetchedMakeup(makeup)
        }
    }

    override suspend fun getMakeup(): LiveData<List<MakeupItem>> {
        return withContext(Dispatchers.IO) {
            return@withContext makeupDao.getMakeup()
        }
    }

    private fun persistFetchedMakeup(fetchedMakeup: Makeup) {
        GlobalScope.launch(Dispatchers.IO) {
            makeupDao.upsert(fetchedMakeup)
        }
    }
}