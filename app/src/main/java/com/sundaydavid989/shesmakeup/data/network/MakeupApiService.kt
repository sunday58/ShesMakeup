package com.sundaydavid989.shesmakeup.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sundaydavid989.shesmakeup.Constants
import com.sundaydavid989.shesmakeup.data.db.entity.Makeup
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MakeupApiService {

    @GET("api/v1/products.json")
    fun getMakeup(): Deferred<Makeup>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): MakeupApiService {
            val requestInterceptor = Interceptor{chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MakeupApiService::class.java)
        }
    }
}