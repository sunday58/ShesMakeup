package com.sundaydavid989.shesmakeup.ui.itemDetails

import androidx.lifecycle.ViewModel
import com.sundaydavid989.shesmakeup.data.db.entity.FavoriteItem
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import com.sundaydavid989.shesmakeup.data.repository.MakeupRepository

class ItemDetailViewModel(
    private val makeupRepository: MakeupRepository
): ViewModel() {

    fun addFavorite(favoriteItem: FavoriteItem){
        makeupRepository.addFavorite(favoriteItem)
    }

    fun deleteFavorite(favorite: FavoriteItem){
        makeupRepository.deleteFavorite(favorite)
    }
}