package com.sundaydavid989.shesmakeup.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sundaydavid989.shesmakeup.data.repository.MakeupRepository

@Suppress("UNCHECKED_CAST")
class ProductTypeDetailFactory(
    private val makeupRepository: MakeupRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProductTypeDetailViewModel(makeupRepository) as T
    }
}