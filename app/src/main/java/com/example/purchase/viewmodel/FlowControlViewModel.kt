package com.example.purchase.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.purchase.data.model.PaymentMethod

class FlowControlViewModel : ViewModel() {

    var amount: String = ""

    var paymentMethod: PaymentMethod? = null

    fun init() {
        amount = "0"
    }


}