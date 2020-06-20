package com.example.purchase.data.model

import com.google.gson.annotations.SerializedName

typealias PaymentMethodsResponse = ArrayList<PaymentMethod>

class PaymentMethod(
    @SerializedName("id") var id: String? = "",

    @SerializedName("name") var name: String? = "",

    @SerializedName("secure_thumbnail") var image: String? = ""
)