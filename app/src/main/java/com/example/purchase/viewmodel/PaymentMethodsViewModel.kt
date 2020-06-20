package com.example.purchase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.purchase.data.Repository
import com.example.purchase.data.RepositoryImpl
import com.example.purchase.data.model.PaymentMethodsResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class PaymentMethodsViewModel(
    val repository: Repository = RepositoryImpl(),
    var data: PaymentMethodsResponse = PaymentMethodsResponse()
) : ViewModel() {

    private var stateMutableLiveData: MutableLiveData<STATE> = MutableLiveData(STATE.NOT_INITIALIZED)

    val stateLiveData: LiveData<STATE>
        get() = stateMutableLiveData

    var positionItemSelected: Int = -1

    fun loadData() {
        GlobalScope.launch {
            try {
                data = repository.getPaymentMethods()
                Timber.d("count:%s", data.count().toString())
                if (data.count() == 0) {
                    Timber.d("STATE.NO_ELEMENTS")
                    stateMutableLiveData.postValue(STATE.NO_ELEMENTS)
                } else {
                    Timber.d("STATE.SUCCESS")
                    stateMutableLiveData.postValue(STATE.SUCCESS)
                }
            }catch (e:Exception) {
                Timber.e(e)
                Timber.d("STATE.ERROR_CONNECTION")
                stateMutableLiveData.postValue(STATE.ERROR_CONNECTION)
            }
        }
    }

    fun setItemSelected() {
        stateMutableLiveData.value = STATE.ITEM_SELECTED
    }
    fun setNoItemSelected() {
        stateMutableLiveData.value = STATE.NO_ITEM_SELECTED
    }

    enum class STATE {
        NOT_INITIALIZED,
        ERROR_CONNECTION,
        NO_ELEMENTS,
        SUCCESS,
        ITEM_SELECTED,
        NO_ITEM_SELECTED
    }
}