package com.sundaydavid989.shesmakeup.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import com.sundaydavid989.shesmakeup.data.db.entity.ProductItem
import com.sundaydavid989.shesmakeup.internal.NoConnectivityException

class MakeupNetworkDataSourceImpl(
    private val makeupApiService: MakeupApiService
) : MakeupNetworkDataSource {

    private val _downloadMakeup = MutableLiveData<Array<MakeupItem>>()
    private val _downloadProductType = MutableLiveData<Array<ProductItem>>()

    override val downloadMakeup: LiveData<out Array<MakeupItem>>
        get() = _downloadMakeup
    override val downloadProductType: LiveData<out Array<ProductItem>>
        get() = _downloadProductType

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

    override suspend fun fetchProductType(name: String) {
        try {
            val fetchMakeup = makeupApiService
                .getProductTypesAsync(name)
                .await()
            _downloadProductType.postValue(fetchMakeup)
        }
        catch (e: NoConnectivityException){
            Log.d("connectivity ", "No internet connection", e)
        }
    }
}