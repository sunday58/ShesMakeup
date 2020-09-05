package com.sundaydavid989.shesmakeup.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import com.sundaydavid989.shesmakeup.internal.NoConnectivityException

class MakeupNetworkDataSourceImpl(
    private val makeupApiService: MakeupApiService
) : MakeupNetworkDataSource {

    private val _downloadMakeup = MutableLiveData<Array<MakeupItem>>()
    override val downloadMakeup: LiveData<out Array<MakeupItem>>
        get() = _downloadMakeup

    override suspend fun fetchMakeup() {
        try {
            val fetchMakeup = makeupApiService
                .getMakeupAsync()
                .await()
                 _downloadMakeup.postValue(fetchMakeup)
        }
        catch (e: NoConnectivityException){
            Log.d("connectivity ", "No internet connection", e)
        }
    }
}