package com.example.purchase.data

import com.example.purchase.data.model.PaymentMethodsResponse

interface Repository {
    fun getPaymentMethods(): PaymentMethodsResponse
}