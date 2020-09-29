package com.sundaydavid989.shesmakeup.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sundaydavid989.shesmakeup.data.repository.MakeupRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ProductTypeDetailFactory(
    private val makeupRepository: MakeupRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductTypeDetailViewModel::class.java)){
            return ProductTypeDetailViewModel(makeupRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}