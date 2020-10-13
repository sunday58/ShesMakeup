package com.sundaydavid989.shesmakeup.ui.favorite

import androidx.lifecycle.ViewModel
import com.sundaydavid989.shesmakeup.data.repository.MakeupRepository
import com.sundaydavid989.shesmakeup.internal.lazyDeferred

class FavoriteViewModel(
    private val makeupRepository: MakeupRepository
) : ViewModel() {

   val favorite by lazyDeferred {
       makeupRepository.getFavorite()
   }
}