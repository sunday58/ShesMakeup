package com.sundaydavid989.shesmakeup.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sundaydavid989.shesmakeup.data.db.MakeupDatabase
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import com.sundaydavid989.shesmakeup.data.network.MakeupApiService
import kotlinx.coroutines.flow.Flow

class MakeupRepositoryImpl(
    private val service: MakeupApiService,
    private val database: MakeupDatabase
) : MakeupRepository{
    override fun getMakeupResultStream(): Flow<PagingData<MakeupItem>> {

        val pagingSourceFactory = { database.makeupItemDao().getMakeup()}
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            remoteMediator = MakeupRemoteMediator(
                service,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun getMakeupByTypeResultStream(query: String): Flow<PagingData<MakeupItem>> {

        Log.d("MakeUpRepository", "New query: $query")
        val pagingSourceFactory = { database.makeupItemDao().makeupByType(query)}
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = 3
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun getMakeupBySearchResultStream(query: String): Flow<PagingData<MakeupItem>> {
        Log.d("MakeUpRepository", "New query: $query")
        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory = { database.makeupItemDao().searchMakeup(dbQuery)}
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = 3
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 30
    }

}