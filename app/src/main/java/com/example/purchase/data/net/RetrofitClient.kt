package com.example.purchase.data.net

import com.example.purchase.data.model.PaymentMethodsResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private val purchasesApi: PurchaseApi

    companion object {
        private const val PUBLIC_KEY = "TEST-952d2fd9-2958-4f11-8fad-13a88e5e6a21"
        private const val BASE_URL = "https://api.mercadopago.com/v1/"
    }

    init {
        val builder = OkHttpClient.Builder()
        val okHttpClient = builder.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        purchasesApi = retrofit.create(PurchaseApi::class.java)
    }

    fun getPaymentMethods(): Call<PaymentMethodsResponse> {
        return purchasesApi.getPaymentMethods(PUBLIC_KEY)
    }
}