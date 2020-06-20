package com.example.purchase.data.net

import com.example.purchase.data.model.PaymentMethodsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PurchaseApi {

    @GET("payment_methods")
    fun getPaymentMethods(@Query("public_key") public_key: String): Call<PaymentMethodsResponse>
}