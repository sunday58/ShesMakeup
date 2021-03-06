package com.sundaydavid989.shesmakeup.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.sundaydavid989.shesmakeup.data.db.entity.FavoriteItem
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import kotlinx.coroutines.flow.Flow

interface MakeupRepository {
    fun getMakeupResultStream(): Flow<PagingData<MakeupItem>>
    fun getMakeupByTypeResultStream(query: String): Flow<PagingData<MakeupItem>>
    fun getMakeupBySearchResultStream(query: String): Flow<PagingData<MakeupItem>>

    // for favorite
    fun addFavorite(favoriteItem: FavoriteItem)
    suspend fun getFavorite(): LiveData<List<FavoriteItem>>
    fun deleteFavorite(favorite: FavoriteItem)
}