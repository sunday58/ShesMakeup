package com.sundaydavid989.shesmakeup.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import com.sundaydavid989.shesmakeup.data.repository.MakeupRepository
import kotlinx.coroutines.flow.Flow

class HomeViewModel(
    private val makeupRepository: MakeupRepository
) : ViewModel() {

    private var currentMakeupResult: Flow<PagingData<MakeupItem>>? = null

    fun getMakeup(): Flow<PagingData<MakeupItem>> {
        val lastResult = currentMakeupResult
        if (lastResult != null){
            return lastResult
        }
        val newResult: Flow<PagingData<MakeupItem>> = makeupRepository.getMakeupResultStream()
            .cachedIn(viewModelScope)
        currentMakeupResult = newResult
        return newResult
    }

}