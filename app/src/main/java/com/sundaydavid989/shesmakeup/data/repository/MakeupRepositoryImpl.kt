package com.sundaydavid989.shesmakeup.data.repository

import androidx.lifecycle.LiveData
import com.sundaydavid989.shesmakeup.data.db.MakeupDao
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import com.sundaydavid989.shesmakeup.data.db.entity.ProductItem
import com.sundaydavid989.shesmakeup.data.network.MakeupNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MakeupRepositoryImpl(
    private val makeupDao: MakeupDao,
    private val makeupNetworkDataSource: MakeupNetworkDataSource
) : MakeupRepository {

    init {
        makeupNetworkDataSource.downloadMakeup.observeForever { makeup ->
            //persist makeups
            persistFetchedMakeup(makeup)
        }
        makeupNetworkDataSource.downloadProductType.observeForever { products ->
            persistFetchedProductType(products)
        }
    }

    override suspend fun getMakeup(): LiveData<List<MakeupItem>> {
        return withContext(Dispatchers.IO) {
            fetchMakeup()
            return@withContext makeupDao.getMakeup()
        }
    }

    override suspend fun getProductType(): LiveData<List<ProductItem>> {
        return  withContext(Dispatchers.IO) {
            return@withContext makeupDao.getProduct()
        }
    }

    private fun persistFetchedMakeup(fetchedMakeup: Array<MakeupItem>) {
        GlobalScope.launch(Dispatchers.IO) {
            makeupDao.upsert(fetchedMakeup)
        }
    }

    private fun persistFetchedProductType(fetchedProduct: Array<ProductItem>){
        GlobalScope.launch(Dispatchers.IO){
            makeupDao.insertProduct(fetchedProduct)
        }
    }

    private suspend fun fetchMakeup(){
        makeupNetworkDataSource.fetchMakeup()
    }

     override suspend fun fetchProductType(name: String){
        makeupNetworkDataSource.fetchProductType(name)
    }
}