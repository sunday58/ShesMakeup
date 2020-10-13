package com.sundaydavid989.shesmakeup.ui.itemDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sundaydavid989.shesmakeup.data.repository.MakeupRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@FlowPreview
@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class ItemDetailViewModelFactory(
    private val makeupRepository: MakeupRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemDetailViewModel::class.java)) {
            return ItemDetailViewModel(makeupRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}