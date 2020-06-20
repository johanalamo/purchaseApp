package com.example.purchase.viewmodel

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class AmountViewModel : ViewModel() {

    var amount = ObservableField<String>("0")

    private var stateMutableLiveData: MutableLiveData<AmountViewModel.STATE> = MutableLiveData(
        AmountViewModel.STATE.AMOUNT_INVALID
    )

    val stateLiveData: LiveData<AmountViewModel.STATE>
        get() = stateMutableLiveData


    fun checkAmount() {
        try {
            if ((amount.get()!!.toDouble() < MIN_AMOUNT) || (amount.get()!!.toDouble() > MAX_AMOUNT)) {
                stateMutableLiveData.value = STATE.AMOUNT_INVALID
            } else {
                stateMutableLiveData.value = STATE.AMOUNT_VALID
            }
        } catch (e: Exception) {
            Timber.e(e)
            stateMutableLiveData.value = STATE.AMOUNT_INVALID
        }
    }

    companion object {
        const val MIN_AMOUNT:Double = 1.0
        const val MAX_AMOUNT:Double = 1000000.0
    }

    enum class STATE {
        AMOUNT_INVALID,
        AMOUNT_VALID
    }
}
