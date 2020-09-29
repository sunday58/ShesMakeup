package com.sundaydavid989.shesmakeup.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import com.sundaydavid989.shesmakeup.data.repository.MakeupRepository
import kotlinx.coroutines.flow.Flow

class SearchViewModel(
    private val makeupRepository: MakeupRepository
) : ViewModel() {

    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<MakeupItem>>? = null

    fun searchMakeup(queryString: String): Flow<PagingData<MakeupItem>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<MakeupItem>> =
            makeupRepository.getMakeupBySearchResultStream(queryString)
                .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}