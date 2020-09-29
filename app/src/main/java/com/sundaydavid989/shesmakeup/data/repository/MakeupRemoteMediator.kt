package com.sundaydavid989.shesmakeup.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.sundaydavid989.shesmakeup.data.db.MakeupDatabase
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import com.sundaydavid989.shesmakeup.data.db.entity.RemoteKeys
import com.sundaydavid989.shesmakeup.data.network.MakeupApiService
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException


private const val MAKEUP_STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class MakeupRemoteMediator(
    private val service: MakeupApiService,
    private val makeupDatabase: MakeupDatabase
): RemoteMediator<Int, MakeupItem>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MakeupItem>
    ): MediatorResult {
        val page = when(loadType){
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: MAKEUP_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                if (remoteKeys == null){
                    throw InvalidObjectException("Remote key and prevKey should not be empty")
                }
                val prevKey = remoteKeys.prevKey
                if (prevKey == null){
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                remoteKeys.prevKey
            }
            LoadType.APPEND -> {
               val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys?.nextKey == null) {
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                }
                remoteKeys.nextKey
            }
        }

        //make api call
        try {
            val apiResponse = service.getMakeupAsync()
            val makeups = apiResponse.items
            val endOfPaginationReached = makeups.isEmpty()
            makeupDatabase.withTransaction {
                if (loadType == LoadType.REFRESH){
                    makeupDatabase.remoteKeysDao().clearRemoteKeys()
                    makeupDatabase.makeupItemDao().clearMakeups()
                }
                val prevKey = if (page == MAKEUP_STARTING_PAGE_INDEX) null else page -1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = makeups.map {
                    RemoteKeys(makeUpId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                makeupDatabase.remoteKeysDao().insertAll(keys)
                makeupDatabase.makeupItemDao().upsert(makeups)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException){
            return MediatorResult.Error(exception)
        } catch (exception: HttpException){
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MakeupItem>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { makeupId ->
                makeupDatabase.remoteKeysDao().remoteKeysMakeUpId(makeupId)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MakeupItem>
    ): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { makeup ->
                makeupDatabase.remoteKeysDao().remoteKeysMakeUpId(makeup.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MakeupItem>
    ): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { makeup ->
                makeupDatabase.remoteKeysDao().remoteKeysMakeUpId(makeup.id)
            }
    }
}