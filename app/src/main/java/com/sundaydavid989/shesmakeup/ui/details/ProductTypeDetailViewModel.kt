package com.sundaydavid989.shesmakeup.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import com.sundaydavid989.shesmakeup.data.repository.MakeupRepository
import kotlinx.coroutines.flow.Flow

class ProductTypeDetailViewModel(
    private val makeupRepository: MakeupRepository
) : ViewModel() {

    private var currentQueryValue: String? = null

    private var currentMakeupResult: Flow<PagingData<MakeupItem>>? = null

    fun searchItemType(queryString: String): Flow<PagingData<MakeupItem>> {
        val lastResult = currentMakeupResult
        if (queryString == currentQueryValue && lastResult != null){
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<MakeupItem>> =
            makeupRepository.getMakeupByTypeResultStream(queryString)
                .cachedIn(viewModelScope)
        currentMakeupResult = newResult
        return newResult
    }
}