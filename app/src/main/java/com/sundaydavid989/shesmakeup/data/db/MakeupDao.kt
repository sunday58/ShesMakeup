package com.sundaydavid989.shesmakeup.data.db

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.sundaydavid989.shesmakeup.data.db.entity.FavoriteItem
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem

@Dao
interface MakeupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(makeupItem: Array<MakeupItem>)

    @Query("SELECT * FROM make_up")
    fun getMakeup(): PagingSource<Int, MakeupItem>

    @Query("DELETE FROM make_up")
    suspend fun clearMakeups()

    //for Makeup by product type
    @Query("SELECT * FROM make_up WHERE productType = :queryString")
    fun makeupByType(queryString: String): PagingSource<Int, MakeupItem>

    //for makeup search
    @Query("SELECT * FROM make_up WHERE name LIKE :queryString")
    fun searchMakeup(queryString: String): PagingSource<Int, MakeupItem>

    //for favorite
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favoriteItem: FavoriteItem)

    @Query("SELECT * FROM favorite")
    fun getFavorite(): LiveData<List<FavoriteItem>>

    @Delete
    fun deleteFavorite(favorite: FavoriteItem)


}