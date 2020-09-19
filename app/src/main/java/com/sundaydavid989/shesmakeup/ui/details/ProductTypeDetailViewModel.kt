package com.sundaydavid989.shesmakeup.ui.details

import androidx.lifecycle.ViewModel
import com.sundaydavid989.shesmakeup.data.repository.MakeupRepository
import com.sundaydavid989.shesmakeup.internal.lazyDeferred

class ProductTypeDetailViewModel(
    private val makeupRepository: MakeupRepository
) : ViewModel() {

    val productType by lazyDeferred {
        makeupRepository.getProductType()
    }

   suspend fun fetchProductType(name: String) {
        makeupRepository.fetchProductType(name)
    }
}