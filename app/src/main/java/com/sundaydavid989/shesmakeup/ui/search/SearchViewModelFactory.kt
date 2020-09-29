package com.sundaydavid989.shesmakeup.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sundaydavid989.shesmakeup.data.repository.MakeupRepository
import com.sundaydavid989.shesmakeup.ui.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.lang.IllegalArgumentException


@FlowPreview
@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(
    private val makeupRepository: MakeupRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)){
            return SearchViewModel(makeupRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}