package com.example.purchase.data

import com.example.purchase.data.model.PaymentMethod
import com.example.purchase.data.model.PaymentMethodsResponse
import com.example.purchase.data.net.RetrofitClient
import timber.log.Timber
import java.io.IOException
import java.lang.Exception

class RepositoryImpl : Repository {

    private val retrofitClient = RetrofitClient()

    override fun getPaymentMethods():PaymentMethodsResponse {
        try {
            var response = retrofitClient.getPaymentMethods().execute()
            if (response.isSuccessful) {
                val body = response.body()
                return body ?: PaymentMethodsResponse()
            } else {
                Timber.e("Body is null")
                throw Exception()
            }
        } catch (e: IOException) {
            Timber.e("IOException")
            throw e
        } catch (e: RuntimeException) {
            Timber.e("RuntimeException")
            throw e
        }
    }
}