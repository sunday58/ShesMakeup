package com.sundaydavid989.shesmakeup.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sundaydavid989.shesmakeup.data.repository.MakeupRepository

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val makeupRepository: MakeupRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(makeupRepository) as T
    }
}