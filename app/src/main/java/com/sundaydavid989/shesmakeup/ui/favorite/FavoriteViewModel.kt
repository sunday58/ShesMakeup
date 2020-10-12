package com.sundaydavid989.shesmakeup.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import com.sundaydavid989.shesmakeup.data.repository.MakeupRepository
import kotlinx.coroutines.*

class FavoriteViewModel(
    private val makeupRepository: MakeupRepository
) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    fun addFavorite(favoriteItem: MakeupItem){
        makeupRepository.addFavorite(favoriteItem)
    }

    fun getFavorite() {
        viewModelScope.launch {
            makeupRepository.getFavorite()
        }
    }
}