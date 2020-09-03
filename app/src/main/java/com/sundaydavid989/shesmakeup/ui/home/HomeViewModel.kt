package com.sundaydavid989.shesmakeup.ui.home

import androidx.lifecycle.ViewModel
import com.sundaydavid989.shesmakeup.data.repository.MakeupRepository
import com.sundaydavid989.shesmakeup.internal.lazyDeferred

class HomeViewModel(
    private val makeupRepository: MakeupRepository
) : ViewModel() {

    val makeup by lazyDeferred {
        makeupRepository.getMakeup()
    }

}