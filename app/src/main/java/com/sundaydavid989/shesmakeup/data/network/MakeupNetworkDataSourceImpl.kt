package com.sundaydavid989.shesmakeup.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sundaydavid989.shesmakeup.data.db.entity.Makeup
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import com.sundaydavid989.shesmakeup.internal.NoConnectivityException

class MakeupNetworkDataSourceImpl(
    private val makeupApiService: MakeupApiService
) : MakeupNetworkDataSource {

    private val _downloadMakeup = MutableLiveData<Makeup>()
    override val downloadMakeup: LiveData<Makeup>
        get() = _downloadMakeup

    override suspend fun fetchMakeup() {
        try {
            val fetchMakeup = makeupApiService
                .getMakeup()
                .await()
                 _downloadMakeup.postValue(fetchMakeup)
        }
        catch (e: NoConnectivityException){
            Log.e("connectivity ", "No internet connection", e)
        }
    }
}